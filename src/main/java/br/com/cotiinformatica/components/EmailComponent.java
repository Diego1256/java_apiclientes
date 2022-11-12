package br.com.cotiinformatica.components;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailComponent {

	// Classe do SpringBoot para envio de emails
	@Autowired // inicialização automática (injeção de dependência)
	JavaMailSender javaMailSender;

	// Ler o valor da configuração feita no arquivo
	// applica.properties referente ao endereço da conta de email
	@Value("${spring.mail.username}")
	String userName;

	// Método para fazer o envio do email
	public void enviarMensagem(EmailComponentModel model) throws Exception {

		/*
		// Criando uma mensagem de email		
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom(userName);
		mailMessage.setTo(email);
		mailMessage.setSubject(assunto);
		mailMessage.setText(corpo);

		// enviando a mensagem de email
		javaMailSender.send(mailMessage);
		*/
		
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		
		helper.setFrom(userName);
		helper.setTo(model.getEmailDest());
		helper.setSubject(model.getAssunto());
		helper.setText(model.getTexto(), model.isHtml());
		
		javaMailSender.send(message);
	}
}