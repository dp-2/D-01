
package services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ConfigurationRepository;
import domain.Configuration;
import domain.DomainEntity;

@Service
@Transactional
public class ConfigurationService extends DomainEntity {

	//Repository-----------------------------------------------------------------

	@Autowired
	private ConfigurationRepository	configurationRepository;

	//Services-------------------------------------------------------------------

	@Autowired
	private AdministratorService	administratorService;


	//Metodos--------------------------------------------------------------------

	public Configuration create() {
		final Configuration configuration = new Configuration();

		configuration.setNameSys("Acme-Madrugá");
		configuration.setBanner("https://tinyurl.com/acme-madruga");
		configuration.setWelcomeMessageEN("Welcome to Acme Madrugá! The site to organise your processions.");
		configuration.setWelcomeMessageES("¡Bienvenidos a Acme Madrugá! Tu sitio para organizar procesiones.");
		configuration.setPositiveWordsEN("good, fantastic, excellent, great, amazing, terrific");
		configuration.setPositiveWordsES("bueno, fantastico, excelente, genial, increible, estupendo");
		configuration.setNegativeWordsEN("not, bad, horrible, average, disaster");
		configuration.setNegativeWordsES("no, malo, horrible, mediocre, desastroso");
		configuration.setSpamWordsEN("sex, viagra, cialis, one million, you`ve been selected, nigeria");
		configuration.setSpamWordsES("sexo, viagra, cialis, un millon, has sido seleccionado, nigeria");
		configuration.setPositionsEN("President, Vice-President, Secretary, Treasurer, Historian, Fundraiser, Officer");
		configuration.setPositionsES("Presidente, Vicepresidente, Secretario, Tesorero, Historiador, Promotor, Vocal");
		configuration.setCacheFinder(1);
		configuration.setNumResults(10);

		configuration
			.setLegalTextEN("1. What is personal data? "
				+ "Personal Data is any information that makes it possible to identify or make identifiable a person. "
				+ "This information is provided when a person accesses the website on their device. These include, but are not limited to, first name, last name, nif, postal and e-mail addresses, as well as telephone numbers."
				+ "In addition, when the website is used, certain information is automatically stored for technical reasons (e.g., the IP address assigned by the users Internet access provider)."
				+ "2. What personal data is used and for what purpose?"
				+ "Any personal data provided to Acme-Madrugá when using the Web will be treated in accordance with applicable data protection laws. They will only be collected, processed and used for lawful and legitimate purposes, about which the user is informed at all times. Acme-Madrugá stores and uses your personal information to make the use of the website as simple and convenient as possible, so that you can register, log in, make an appointment in the shops, have your shopping list, formalize a contract through a link to our website, send information or contact Acme-Madrugá."
				+ "Also, when these personal data are collected through the forms that are included on the Web, it will be necessary for the user to complete all the data requested, because if they are not provided, considered necessary, the service requested can not be managed or resolve the query made."
				+ "Certain technical information is necessary for the Web to continue to be useful in the users mobile device. Such technical information will be stored and used anonymously for the development of analytics, statistics, and with the aim of improving the Web. The user will be able to indicate which are his preferences through his user account, or to eliminate the same ones if he is opposed to it."
				+ "Additionally, personal data and technical information are also stored and used when necessary to monitor and prevent unauthorized use or activities that may be illegal (e.g., to maintain and ensure security in the event of attacks on our computer systems)."
				+ "Finally, the users information is stored and used if required by applicable legislation, or, if necessary, to comply with requests from competent public and/or administrative bodies, as well as to make possible the exercise of Acme-Madrugás rights and actions, and for any other purpose contemplated in the applicable laws."
				+ "3. Do we communicate your personal data?"
				+ "Certain information may be processed by certain service providers who have been selected with special care and who only use it in accordance with our instructions and instructions (including, but not limited to, customer service, central computer services, companies conducting market research or customer satisfaction surveys, as well as payment method providers who perform security checks on our behalf to prevent and prevent fraud or other problems during the payment process). In this regard, information will only be sent to entities that are located within the European Economic Area and, therefore, subject to European Union data protection regulations, or that commit to comply with the corresponding data protection standards."
				+ "Finally, we will communicate your data and information, in accordance with data protection rules, to third parties or public bodies if required to do so by virtue of requests from a competent authority, as well as when required to do so by courts and tribunals for any reason."
				+ "4. Users rights."
				+ "Users have recognised rights of access, rectification, cancellation and opposition, which they may exercise free of charge before Acme-Madrugá. The quickest and easiest way to exercise their rights is by accessing the user account on the Web and modifying their data. Any information that we need to store, by virtue of a legal or contractual obligation, will be blocked and will only be used for such purposes instead of being erased.");

		configuration
			.setLegalTextES("1. ¿Qué son datos personales?"
				+ "Datos Personal es cualquier información que permite identificar o hacer identificable a una persona. Esta información se facilita cuando una persona accede  en su dispositivo a la web."
				+ "Entre otros, se incluyen el nombre, apellidos, nif, la dirección postal y electrónica, así como el número de teléfono entre otros."
				+ "Adicionalmente, cuando se usa la web, determinada información es almacenada de forma automática por motivos técnicos (p.e., la dirección IP asignada por el proveedor de acceso a Internet del usuario)."
				+ "2. ¿Qué datos personales son utilizados y para qué finalidad?"
				+ "Cualquier dato personal que se facilite a Acme-Madrugá al usar la Web será tratado de conformidad con las leyes de protección de datos aplicables. Sólo serán recogidos, tratados y utilizados para fines lícitos y legítimos, sobre los cuales se informa al usuario en todo momento. Acme-Madrugá almacena y utiliza su información personal para hacer que el uso de la Web sea lo más sencillo y cómodo posible, para que pueda registrarse, iniciar sesión, solicitar cita previa en las tiendas, tener la lista de sus compras, formalizar un contrato a través de un enlace a nuestro sitio web, enviar información o contactar con Acme-Madrugá."
				+ "Asimismo, cuando estos datos personales sean recabados a través de los formularios que se incluyen en la Web, será necesario que el usuario complete todos los datos solicitados, ya que si no se suministrasen los mismos, considerados necesarios, no se podrá gestionar el servicio solicitado o resolver la consulta formulada."
				+ "Determinada información técnica es necesaria para que la Web siga siendo de utilidad en el dispositivo móvil del usuario. Tal información técnica será almacenada y utilizada de forma anónima para el desarrollo de analíticas, estadísticas, y con el objetivo de mejorar la Web. El usuario podrá indicar cuáles son sus preferencias a través de su cuenta de usuario, o eliminar las mismas si se opone a ello."
				+ "Adicionalmente, también se almacenan y se utilizan datos personales e información técnica cuando es necesario para monitorizar y prevenir usos no autorizados o actividades que puedan ser ilegales (p.e., para mantener y garantizar la seguridad en caso de ataques a nuestros sistemas informáticos)."
				+ "Finalmente, se almacena y se utiliza la información del usuario en caso de ser requerido por la legislación aplicable, o, si fuera necesario, para dar cumplimiento a requerimientos procedentes de órganos públicos y/o administrativos competentes, así como para hacer posible el ejercicio de los derechos y acciones de Acme-Madrugá, y para cualquier otra finalidad contemplada en las leyes aplicables."
				+ "3. ¿Comunicamos sus datos personales?"
				+ "Determinada información puede ser tratada por ciertos proveedores de servicios que han sido seleccionados con especial cuidado, que únicamente la utilizan siguiendo nuestras instrucciones e indicaciones (entre otros, cabe incluir servicios de atención al cliente, servicios informáticos centrales, compañías que desarrollan investigaciones de mercado o encuestas de satisfacción de clientes, así como proveedores de medios de pago que realizan comprobaciones de seguridad en nuestro nombre para evitar y prevenir fraudes u otros problemas durante el proceso de pago). En este sentido, solo se enviará información a entidades que estén ubicadas dentro del Espacio Económico Europeo y, por tanto, sujetas a las normas de la Unión Europea sobre protección de datos, o que se comprometan a cumplir con los correspondientes estándares de protección de datos."
				+ "Finalmente, comunicaremos sus datos e información, de conformidad con las normas sobre protección de datos, a terceras partes u organismos públicos en caso de ser requeridos para ello en virtud de solicitudes procedentes de una autoridad competente, así como cuando se nos requiera por juzgados y tribunales por cualquier motivo."
				+ "4. Derechos de los usuarios."
				+ "Los usuarios tienen reconocidos los derechos de acceso, rectificación, cancelación y oposición, que podrán ejercitar gratuitamente ante Acme-Madrugá. La forma más rápida y sencilla para ejercitar sus derechos es accediendo a la cuenta de usuario en la Web y modificando sus datos. Cualquier información que necesitemos almacenar, en virtud de una obligación legal o contractual, será bloqueada y solo será utilizada para tales fines en lugar de ser borrada.");

		configuration
			.setCookiesTextEN("What is a cookie and what is it for?"
				+ "A Cookie is a small file that is stored on the users computer, tablet, smartphone or any other device with information about navigation."
				+ "The set of cookies of all our users helps us to improve the quality of our website, allowing us to control which pages are useful, which are not and which are susceptible to improvement."
				+ "Cookies are essential for the functioning of the Internet, providing countless advantages in the provision of interactive services, facilitating navigation and usability of our website. Under no circumstances could cookies damage your computer. On the other hand, the fact that they are active helps us to identify and resolve errors."
				+ "We use cookies to recognize your browser, your device, to learn more about your interests and to provide you with essential tools and services and for other purposes, for example:"
				+ "Recognize when you register to use our services, which allows us to offer you product recommendations, show you personalized content, recognize you as a member, allow you to use features more comfortably, offer you other features and personalized services."
				+ "Maintain a record of saved products."
				+ "Conduct research and diagnostics to improve the content, products and services of Acme-Madrugá."
				+ "Prevent fraudulent activities."
				+ "Improve security."
				+ "Submit content, including advertisements, Acme-Madrugá and third-party pages that may be of interest to you."
				+ "Inform. This allows us to consider and analyze the performance of our services."
				+ "Acme-Madrugá cookies offer you the possibility to take advantage of many of the essential functionalities of Acme-Madrugá. For example, if you reject or block our cookies, you will not be able to access certain features, access the termination page, or use any of Acme-Madrugás products or services that require you to log in."
				+ "Authorized third parties may also use cookies when you interact with Acme-Madrugá services including search engines, measurement and analytical service providers, social networks, and advertising companies. Third parties use cookies in the process to deliver content, including ads relevant to your interests, to measure the effectiveness of their ads, and to deliver services on behalf of Acme-Madrugá. Click here to learn more about third parties that may use cookies when you use Acme-Madrugá services."
				+ "You can manage your browsers cookies through your browser settings. The Help feature on most browsers tells you how to set your browser not to accept new cookies, to notify you each time you receive a new cookie, how to disable cookies, and when cookies will expire. If you turn off all cookies in your browser, neither we nor third parties will transfer cookies to your browser. However, you may have to adjust some of your preferences manually whenever you visit a page and some of the tools and services do not work.");

		configuration
			.setCookiesTextES("¿Qué es una cookie y para qué sirve?"
				+ "Una Cookie es un pequeño archivo que se almacena en el ordenador del usuario, tablet, smartphone o cualquier otro dispositivo con información sobre la navegación."
				+ "El conjunto de cookies de todos nuestros usuarios nos ayuda a mejorar la calidad de nuestra web, permitiéndonos controlar qué páginas son útiles, cuáles no y cuáles son susceptibles de mejora."
				+ "Las cookies son esenciales para el funcionamiento de internet, aportando innumerables ventajas en la prestación de servicios interactivos, facilitándote la navegación y usabilidad de nuestra web. En ningún caso las cookies podrían dañar tu equipo. Por contra, el que estén activas nos ayuda a identificar y resolver los errores."
				+ "Utilizamos cookies para reconocer tu navegador, tu dispositivo, para saber más sobre tus intereses y para proporcionarte herramientas esenciales y servicios y para otros fines, por ejemplo:"
				+ "Reconocer cuando te registras para usar nuestros servicios, lo que nos permite ofrecerte recomendaciones de productos, mostrarte contenido personalizado, reconocerte como miembro, permitirte utilizar funcionalidades mas comodamente, ofrecerte otras funcionalidades y servicios personalizados."
				+ "Mantener un registro de los productos guardados."
				+ "Llevar a cabo investigaciones y diagnósticos para mejorar el contenido, los productos y los servicios de Acme-Madrugá."
				+ "Prevenir actividades fraudulentas."
				+ "Mejorar la seguridad."
				+ "Enviar contenidos, incluyendo anuncios, páginas de Acme-Madrugá y de terceros que podrían interesarte."
				+ "Informar. Esto nos permite considerar y analizar el rendimiento de nuestros servicios."
				+ "Las cookies de Acme-Madrugá te ofrecen la posibilidad de aprovechar muchas de las funcionalidades esenciales de Acme-Madrugá. Por ejemplo, si rechazas o bloqueas nuestras cookies, no podrás acceder a determinadas funcionalidades, acceder a la página de finalización ni usar ninguno de los productos o servicios de Acme-Madrugá que requieren que inicies una sesión."
				+ "Los terceros autorizados también pueden utilizar cookies cuando interactúas con los servicios de Acme-Madrugá entre los que se incluyen motores de búsqueda, proveedores de servicios de medición y analíticos, redes sociales y compañías de publicidad. Los terceros utilizan cookies en el proceso para proporcionar contenidos, incluyendo anuncios relevantes para tus intereses, para medir la efectividad de sus anuncios y para prestar servicios en nombre de Acme-Madrugá. Haz clic aquí para saber más sobre los terceros que podrían utilizar cookies cuando utilizas los servicios de Acme-Madrugá."
				+ "Puedes gestionar las cookies de tu navegador a través de la configuración de tu navegador. En la función “Ayuda” de la mayoría de los navegadores se indica cómo configurar tu navegador para que no acepte cookies nuevas, para que te notifique cada vez que recibes una nueva cookie, cómo desactivar cookies y cuándo caducarán las cookies. Si desactivas todas las cookies en tu navegador, ni nosotros ni terceros transferiremos cookies a tu navegador. Sin embargo, es probable que tengas que ajustar algunas de tus preferencias manualmente siempre que visites una página y que algunas de las herramientas y servicios no funcionen.");

		return configuration;
	}

	public Configuration findOne() {
		final List<Configuration> configurations = new ArrayList<>(this.configurationRepository.findAll());
		return configurations.get(0);
	}

	public Configuration save(final Configuration configuration) {
		Assert.notNull(configuration);
		Assert.isTrue(this.administratorService.isPrincipalAdmin(), "noAdmin");

		final Configuration saved = this.configurationRepository.save(configuration);

		return saved;
	}

	//Otros-----------------------------------------------------------------------

	public Configuration findSettings() {
		return this.configurationRepository.findConfigurations();
	}
}
