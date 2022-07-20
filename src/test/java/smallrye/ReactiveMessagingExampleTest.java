package smallrye;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.reactive.messaging.providers.connectors.InMemoryConnector;
import io.smallrye.reactive.messaging.providers.connectors.InMemorySink;
import io.smallrye.reactive.messaging.providers.connectors.InMemorySource;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.enterprise.inject.Any;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ReactiveMessagingExampleTest {
	// 1. Switch the channels to the in-memory connector:
	@BeforeAll
	public static void switchMyChannels() {
		InMemoryConnector.switchIncomingChannelsToInMemory("prices");
		InMemoryConnector.switchOutgoingChannelsToInMemory("processed-prices");
	}

	// 2. Don't forget to reset the channel after the tests:
	@AfterAll
	public static void revertMyChannels() {
		InMemoryConnector.clear();
	}

	// 3. Inject the in-memory connector in your test,
	// or use the bean manager to retrieve the instance
	@Inject
	@Any
	InMemoryConnector connector;

	@Test
	void test() {
		// 4. Retrieves the in-memory source to send message
		InMemorySource<Integer> prices = connector.source("prices");
		// 5. Retrieves the in-memory sink to check what is received
		InMemorySink<Integer> results = connector.sink("processed-prices");

		// 6. Send fake messages:
		InMemorySource<Integer> send = prices.send(1);
		assertThat(send).isNotNull();

		prices.send(2);
		prices.send(3);

		// 7. Check you have receives the expected messages
		List<? extends Message<Integer>> received = results.received();
		assertThat(received).hasSize(3);
	}
}
