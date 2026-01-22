package com.github.ishopping.clients;

import com.github.ishopping.clients.controller.ClientController;
import com.github.ishopping.clients.model.Client;
import com.github.ishopping.clients.service.ClientService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@SpringBootTest
class ClientsApplicationTests {

	@Test
	public void save_returnsOkAndSavesClient() {
		ClientService clientService = mock(ClientService.class);
		ClientController controller = new ClientController(clientService);

		Client client = new Client();
		client.setId(1L);
		client.setName("Alice");

		ResponseEntity<Client> response = controller.save(client);

		verify(clientService).save(client);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertSame(client, response.getBody());
	}

	@Test
	public void getById_returnsClientWhenFound() {
		ClientService clientService = mock(ClientService.class);
		ClientController controller = new ClientController(clientService);

		Client client = new Client();
		client.setId(2L);
		client.setName("Bob");

		when(clientService.getById(2L)).thenReturn(Optional.of(client));

		ResponseEntity<Client> response = controller.getById(2L);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertSame(client, response.getBody());
	}

	@Test
	public void getById_returnsNotFoundWhenMissing() {
		ClientService clientService = mock(ClientService.class);
		ClientController controller = new ClientController(clientService);

		when(clientService.getById(99L)).thenReturn(Optional.empty());

		ResponseEntity<Client> response = controller.getById(99L);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertNull(response.getBody());
	}

	@Test
	public void delete_deletesExistingClientAndReturnsNoContent() {
		ClientService clientService = mock(ClientService.class);
		ClientController controller = new ClientController(clientService);

		Client client = new Client();
		client.setId(3L);
		client.setName("Carol");

		when(clientService.getById(3L)).thenReturn(Optional.of(client));

		ResponseEntity<Void> response = controller.delete(3L);

		verify(clientService).delete(client);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		assertNull(response.getBody());
	}

	@Test
	public void delete_throwsResponseStatusExceptionWhenClientNotFound() {
		ClientService clientService = mock(ClientService.class);
		ClientController controller = new ClientController(clientService);

		when(clientService.getById(404L)).thenReturn(Optional.empty());

		ResponseStatusException ex = assertThrows(ResponseStatusException.class, () -> controller.delete(404L));
		assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
	}


}
