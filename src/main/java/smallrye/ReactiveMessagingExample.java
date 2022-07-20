package smallrye;

import io.smallrye.mutiny.Multi;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ReactiveMessagingExample {

	@Incoming("prices")
	@Outgoing("processed-prices")
	int process(int price) {
		return price;
	}
}
