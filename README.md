# Něco málo o tomto projektu (obvyklá nuda)

[Click here](./docs/project.md).

# Zkušenosti

## SOAP

<http://en.wikipedia.org/wiki/List_of_web_service_specifications>

Ouch... to se vážně nemám už nikdy v životě nudit?

### CXF

Rozhodně lepší než Axis. V zásadě funguje dobře, ale obsahuje bugy a nezabývá se detaily. Typická známka příliš rychle se rozvíjejícího nebo nesprávně vedeného projektu...

#### Co se mi líbí

Poměrně dobře podporuje konverzi výjimek na SOAP fault zprávy. Na druhou stranu nefunguje přenastavení jména XML elementu, na který se výjimka konvertuje...

Detaily:  
<https://www.google.cz/search?q=xf+maven&ie=utf-8&oe=utf-8&gws_rd=cr&ei=6HpGVerrC5LfaIXBgbgD#q=cxf+maven+generate>

#### Problémy

**Generování WSDL z Eclipse**

* URI služby není sestavena správně,
* fully-qualified-classname (FQC) není v `cxf-beans.xml` updatováno při změně,
* některé JAX-WS anotace, resp. jejich atributy, nejsou při generování použity nebo se nesprávně propagují.

**Statičnost některých atributů/konfigurací**

Například `@WebService(endpointInterface = "{SEI-FQC}")` - FCQ je definována jako `String`. Správně by měla být definována jako `Class<?>`, tedy takto: `@WebService(endpointInterface = org.mysei.Sei.class)`.

**Propojení s JAXB**

Aktuálně nelze přepnout na XStream, který je možná i lepší a spolehlivější. Nehledě na fakt, že by člověk nemusel studovat další (a nudný) anotační systém.

**Jednotná úprava výstupních XML**

Je prakticky nemožné dosáhnout, aby měly všechny výstupní XML elementy:
* globálně definovaný a shodný namespace,
* shodnou úpravu (velká, malá písmena).

#### Poznámky

Kontextová menu pro generování artefaktů (WSDL, Java) jsou určitě pěkná, nicméně neumožňují žádné pořádné nastavení/přizpůsobení. Ani nastavení v Eclipse na tom nejsou nijak úžasně...

Proto se velmi vyplatí převést projekt na Maven a používat na veškerou manipulaci s CXF Maven tasky, které:
* umožňují o dost více nastavení,
* ušetří spoustu času (oproti Wizardům a klikání),
* můžeme snadno navěsit na samotný buildovací proces!

Řečeno jinými znaky:
* <http://cxf.apache.org/docs/using-cxf-with-maven.html>
* <http://cxf.apache.org/docs/maven-java2ws-plugin.html>
* <http://cxf.apache.org/docs/maven-java2wsdl-plugin-cxf-20x-only-removed-in-21-and-replaced-with-java2ws.html>
* <http://cxf.apache.org/docs/maven-cxf-codegen-plugin-wsdl-to-java.html>





## REST

Je vcelku jednoduchý a přímočarý. Jediný "problém" je nejspíš (alespoň pro Javu) v nalezení dobrého klienta:

### Klienti

Možností je několik:

1. [Unirest](http://unirest.io/java.html)
	* Vypadá z nich asi nejlépe - nádherný deklarativní styl a vše potřebné. Používá ovšem knihovnu `Apache HTTPComponents`, která koliduje s CXF a to tak, že absolutně nesmiřitelně! Jojo, tak to bývá :(.
2. [Restfulie](https://github.com/caelum/restfulie-java)
	* Nějaké takové trochu divné (špatná dokumentace), ale dá se a po Unirestu je to asi druhá volba, přestože nemá tolik možností...
3. [http-rest-client](https://github.com/g00dnatur3/http-rest-client)
	* Vypadá docela dobře (podobně jako Unirest), podporuje ovšem pouze JSON. Dokáže-li někdo hezky přidat XML, mohl by být i lepší než Restfulie.
4. [Restlet](http://restlet.com/)
	* Těžká tonáž nejvyššího kalibru... Šíleně robustní, ale určitě i velmi dobrý framework.
5. [JAX-RS Client API](http://cxf.apache.org/docs/jax-rs-client-api.html)
	* Nějaké takové divné a nemastné neslané...
6. [RESTEasy](http://docs.jboss.org/resteasy/docs/3.0.9.Final/userguide/html_single/index.html#RESTEasy_Client_Framework) (nadstavba nad JAX-RS)
	* Už o něco lepší než JAX-RS, nicméně pořád tak nějak "divně robustní"...
7. [Jersey](http://www.javacodegeeks.com/2012/09/simple-rest-client-in-java.html)
	* Nejsem si jist, ale vypadá jako nadstavba JAX-RS a opět o něco "lepší", přestože to pořád ještě není "ono".
8. [java.net](http://www.mkyong.com/webservices/jax-rs/restfull-java-client-with-java-net-url/) (vlastní low-level implementace)
	* Občas by se možná mohlo hodit...

**Pravděpodobně by bylo rychlejší, příjemnější a lepší, abychom na hodinách místo curl zkoušeli REST pomocí jUnit testů, kde bude použit jeden z výše uvedených frameworků :).**

### Jersey, Tomcat, Dependency management system

Myslím, že pohodlnější inkludování Jersey do projektů by bylo skrze DMS, např. Ivy/Maven.

Chceme-li použít Ivy, musíme nainstalovat příslušný plugin (`Apache IvyDE`). Otevřete si v Eclipse MarketPlace a hledejte `ivy`. Příklad pro Ivy (`ivy.xml`):

```
<?xml version="1.0"?>
<!DOCTYPE ivy-module [
	<!ENTITY jersey.version "1.19">
]>
<ivy-module version="2.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:noNamespaceSchemaLocation="http://ant.apache.org/ivy/schemas/ivy.xsd">
	<info organisation="org.skycrawl" module="ws_demo" />
	<configurations>
		<conf name="default" />
	</configurations>
	<dependencies defaultconf="default" defaultconfmapping="default->default">
	
		<!-- REST RUNTIME (SERVER) -->
		<dependency org="com.sun.jersey" name="jersey-server" rev="&jersey.version;" />
		<dependency org="com.sun.jersey" name="jersey-servlet" rev="&jersey.version;" />
		<dependency org="com.sun.jersey" name="jersey-core" rev="&jersey.version;" />
		<dependency org="javax.ws.rs" name="jsr311-api" rev="1.1.1" />
	
		<!-- MISCELLANEOUS (SERVER) -->
    	<dependency org="com.google.guava" name="guava" rev="18.0" />
		
	</dependencies>
</ivy-module>
```

Tento soubor je třeba doplnit souborem `ivysettings.xml`:

```
<?xml version="1.0" encoding="UTF-8"?>
<ivysettings>
	<settings defaultResolver="default" />
	<resolvers>
		<chain name="default">
			<!-- Public Maven repository -->
			<ibiblio name="public" m2compatible="true" />
		</chain>
	</resolvers>

</ivysettings>
```

Nyní může Ivy stahovat dependence přímo z [Maven Central Repository](http://search.maven.org/), kde se dají snadno vyhledávat. Podobně by bylo dobré nainkludovat REST klienta a JUnit, všechno snadno, pohromadě, připraveno k využití a nezávisle na IDE (NetBeans, Eclipse, IntelliJ, ...).

Pak:
* Stačí přidat shromážděné knihovny (classpath entry `Ivy`) do `deployment assembly` v projektu Eclipse.
* Bude aktualizace Jersey typicky velmi triviální - stačí změnit danou XML entitu navrchu příkladu. JSR se bude muset měnit jen velmi zřídka a to, když aktualizujeme na novější verzi JAX-RS.

V Mavenu by to vypadalo velmi podobně :).

**POZNÁMKA:** připojil jsem i odkaz na Guavu od Googlu, protože obsahuje užitečnou třídu `MediaType`, kde je definováno mnoho mime typů pro webové aplikace.

### Vyplatí se vědět

#### Parametry a URL kódování

Zde je parametr `query` automaticky dekódován, než je předán:

```
@GET
@Path("...")
@Consumes("...")
@Produces("...")
public String decoded(@QueryParam("...") String query)
{
	...
}
```

Zde je parametr `query` předán v původním stavu, tak jak dorazil v URL:

```
@GET
@Path("...")
@Consumes("...")
@Produces("...")
@Encoded
public String decoded(@QueryParam("...") String query)
{
	...
}
```

#### Kódování znaků

Abychom se vyhnuli zbytečnému bolení hlavy, máme-li co dočinění se službami zpracovávajícími speciální znaky nebo jazyky, vždy nastavme například:
* `Content-Type: text/plain; charset=UTF-8`
* `Accept: text/html; charset=UTF-8`

Servery s oblibou odmítají zpracovávat requesty (`400 - Bad Request`), najdou-li znak, který nezná jakási jejich defaultní znaková sada, nejčastěji pravděpodobně ISO-8859-1. A to se prosím netýká URL, nýbrž `Request Body` (`Content`)!

Vícero souvisejícího počteníčka např. pro Tomcat:  
<http://wiki.apache.org/tomcat/FAQ/CharacterEncoding>

#### Enkódovaná lomítka

Servlet containery často nepovolují enkódovaná lomítka (`%2F`) v URL cestě - requesty končí chybou `400 - Bad Request`. Volání přes parametry tedy funguje (např. `search?search=%2F`), kdežto volání přes cestu nefunguje (např. `/search/%2F`)...

Zatím jsem pouze používal Jersey, takže nevím jak jsou na tom ostatní běhová prostředí pro JAX-RS. Jersey nicméně tvrdí, že oni nic... že všechno servlet containery a "špatná" JAX-RS specifikace :). Detaily:
* <https://java.net/jira/browse/JERSEY-649>
* <https://java.net/jira/browse/JERSEY-329>

S problémem jsem se osobně setkal a je poměrně zapeklitý, minimálně v kombinaci:
* Tomcat v7.0.62
* Jersey v1.19

Nefungovalo žádné z doporučených řešení:
* `System.setProperty("org.apache.tomcat.util.buf.UDecoder.ALLOW_ENCODED_SLASH", "true");`
* <http://stackoverflow.com/questions/2291428/jax-rs-pathparam-how-to-pass-a-string-with-slashes-hyphens-equals-too>

Nezkoušel jsem nastavit parametr z prvního bodu jako JVM argument, protože člověk nemá vždycky možnost tyhle věci ovlivnit a navíc potom tohle nastavení ovlivňuje VŠECHNO (aplikace, servlety, služby, ...), což také nemusí být žádoucí. Přecejen to tam je z dobrého důvodu :).

Jestliže selže vše ostatní, prakticky nezbývá než se vyhnout použití enkódovaných lomítek v cestách, a to:
* změnou návrhu aplikace tak, abychom je vůbec nemuseli použít,
* změnou návrhu aplikace, abychom je používali pouze v URL parametrech nebo `Request Body`.

URL parametry demonstruje například tento tutoriál:  
<http://docs.oracle.com/cd/E19776-01/820-4867/6nga7f5np/index.html>

**POZNÁMKA: všimněte si také anotace `@DefaultValue`.**

Request body lze nahlédnout například tady:  
<http://stackoverflow.com/questions/1725315/how-to-get-full-rest-request-body-using-jersey>

**POZNÁMKA: všimněte si také, že je použit POST. Věc se má tak, že HTTP GET requesty sice můžou mít tělo, nicméně některé webové/aplikační servery je mohou vesele ignorovat poněvadž jim nepřikládají sémantickou hodnotu. A pozor... na to samé může člověk narazit i u knihoven! Důkaz, že nekecám:**

```
$.ajax({
	method: "GET",
		headers:
		{
			"Content-Type": "text/plain; charset=UTF-8",
		    "Accept": "text/html; charset=UTF-8"
		},
		url: "./myService",
		processData: false,
		data: query,
		success: function(responseHtml, textStatus, jqXHR)
		{
			...
		},
		error: function(jqXHR, textStatus, errorThrown)
		{
			...
		}
	});
	
=> myService?{query}
```

Nahradíme-li `GET` za `POST`, budeme žasnout nad rozdílem :). Zkoušeno na jQuery v2.1.4.





## Javou vzhůru k šíleným zítřkům

Používá-li člověk spousty externích knihoven (nebo kupříkladu jen dvě větší), může narazit na spoustu problémů a občas by možná bylo i lepší uchýlit se místo jejich řešení do cvokhausu!

### Konfliktní dependence

Jinými slovy, je-li nějaká knihovna vyžadována několikrát v různých verzích... Noční můra pro všechny, kteří mají podivné problémy a nejsou schopni je vygooglit nebo vyřešit.

Vzbudíte-li se i vy do noční můry, možná vám bude světlým bodem na obzoru následující:
<http://www.javaworld.com/article/2077837/java-se/hello--osgi--part-1--bundles-for-beginners.html>

### Různé logovací systémy

Jednou je "hardkódován" standardní logovací modul Javy, podruhé se spoléhá na SLF4J a do třetice všeho ukecávajícího je nám servírován Log4j. A pokud možno, v různých verzích, prosím... jinak je přece život o ničem! :+1: 




## Ready-to-use externí webové služby

**Pakly:**
* CDYNE (placené): <https://www.cdyne.com/>
* Bing Maps (zdarma v jistém rozmezí): <https://msdn.microsoft.com/en-us/library/dd877956.aspx>

**IP geolokace:**
* Jakýsi seznam: <http://www.programmableweb.com/news/61-geolocation-apis-panoramio-google-gears-and-yahoo/2012/03/14>
* Jakýsi druhý seznam zdarma: <http://www.programmableweb.com/news/7-free-geocoding-apis-google-bing-yahoo-and-mapquest/2012/06/21>
* Konkrétní služba (i zdarma), nicméně horší kvality: <http://www.ipinfodb.com/>
* Konkrétní služba (i zdarma), lepší kvality: <http://ip-api.com/docs/api:xml>	

**Počasí:**
* Dobrý seznámek, zdá se: <http://stackoverflow.com/questions/8446360/which-weather-data-web-services-do-you-know>




## Bonbonek nakonec

Vzdálené nasazení na [koding.com](https://koding.com) pomocí Eclipse a Ant buildfile: `remote.xml`.

Procedura (hlavní body):

1. Vytvořit účet na koding.
2. Připravit koding:
	```
	kpm install tomcat
	sudo apt-get install tomcat7-admin

	# INFO: http://#{vaše-vm}.koding.io:8080/
	# toto přidat do "./conf/tomcat-users.xml":
	# <role rolename="manager-script"/>
	# <user username="ant" password="vaše-heslo" roles="manager-script"/>
	```
3. Vygenerovat `ws_demo.war` do složky `build`.
4. Zbytek je popsán přímo v `remote.xml`.

Jakmile je všechno připraveno a nainstalováno, stačí pro opětovné nasazení pouze:

1. Vygenerovat `ws_demo.war` do složky `build`.
2. Spustit vytvořenou "run konfiguraci" pro `remote.xml`.

Celý proces (kromě instalace) by šlo samozřejmě přepracovat do Mavenu, leč bylo by to trochu složitější a musel by se i trochu přepracovat projekt, včetně dependencí. Chvilku by to trvalo, ale pak by člověk měl jednotné rozhraní na všechno, včetně správy projektu a pak by nastala Nirvana v podobě nezávislosti na Eclipse :).

Nasazení na Heroku (PaaS) by mělo být dokonce ještě jednodušší než nasazení na "generický server s Tomcatem":
<https://www.youtube.com/watch?v=6gYDLFVI07A>





## Užitečné poznámky a odkazy

* <http://en.wikipedia.org/wiki/List_of_web_service_frameworks>
	* chybí ovšem nové dynamické jazyky, které začínají také hodně fušovat do webových aplikací (třeba Python a Ruby)
* http://en.wikipedia.org/wiki/Universal_Description_Discovery_and_Integration
	* pokud vyvíjíte business službu
* kromě WS probíraných/zmiňovaných na přednášce:
	* <http://en.wikipedia.org/wiki/JSON-WSP>
	* <http://en.wikipedia.org/wiki/Common_Object_Request_Broker_Architecture>
* Nevíte, kam svojí webovou službu strčit? Tohle by vám mohlo pomoci:
	* <http://www.paasify.it/vendors>
