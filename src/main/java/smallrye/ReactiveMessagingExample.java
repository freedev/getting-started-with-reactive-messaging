package smallrye;

import io.smallrye.mutiny.Multi;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ReactiveMessagingExample {
	@Outgoing("source")
	public Multi<String> source() {
		return Multi.createFrom().items("hello", "from", "SmallRye", "reactive", "messaging");
	}

//	@Outgoing("processed-a")
//	public String toUpperCase(String payload) {
//		return payload.toUpperCase();
//	}
//
//	@Incoming("processed-a")
//	@Outgoing("processed-b")
//	public Multi<String> filter(Multi<String> input) {
//		return input.select().where(item -> item.length() > 4);
//	}

	@Incoming("source")
	public void sink(String word) {
		System.out.println(">> " + word);
	}
}
