import com.henriquebarucco.fsmshipping.infra.entrypoint.scheduler.dto.MessageDataDto
import com.henriquebarucco.fsmshipping.infra.entrypoint.scheduler.dto.MessageDto
import com.henriquebarucco.fsmshipping.infra.entrypoint.scheduler.dto.MessageEventDto
import kotlinx.serialization.json.Json
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.UUID
import kotlin.random.Random

@Component
class SeederSchedule(
    @Value("\${rabbitmq.queues.shipping.exchange}")
    private val exchange: String,
    @Value("\${rabbitmq.queues.shipping.routing-key}")
    private val routingKey: String,
    private val rabbitTemplate: RabbitTemplate,
) {
    private val messages = mutableListOf<MessageDto>()

    @Scheduled(cron = "*/10 * * * * *")
    fun seed() {
        println("sdfsdf")
        if (messages.size < 3) {
            val message = randomMessage()
            messages.add(message)
            sendMessage(message)
        } else {
            val originalMessage = messages.random()
            val invoiceMessage =
                originalMessage.copy(
                    event = MessageEventDto.INVOICE_CREATED,
                    data = originalMessage.data.copy(invoice = UUID.randomUUID()),
                )
            messages.remove(originalMessage)
            sendMessage(invoiceMessage)
        }
    }

    private fun randomMessage(): MessageDto =
        MessageDto(
            id = UUID.randomUUID(),
            event = MessageEventDto.SHIPPING_CREATED,
            data =
                MessageDataDto(
                    name = "Random Name ${Random.nextInt(1, 100)}",
                ),
        )

    private fun sendMessage(message: MessageDto) {
        val messageString = Json.encodeToString(MessageDto.serializer(), message)
        this.rabbitTemplate.convertAndSend(
            exchange,
            routingKey,
            messageString,
        )
    }
}
