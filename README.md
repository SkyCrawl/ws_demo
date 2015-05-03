# Zkušenosti

## SOAP

Ajta krajta... kolik toho musím číst? Téměř doživotní záruka, že se člověk nebude nikdy nudit. :+1: 

### CXF

Rozhodně lepší než Axis. V zásadě funguje dobře, ale obsahuje bugy a nezabývá se detaily. Typická známka příliš rychle se rozvíjejícího projektu nebo nesprávně vedeného projektu...

#### Co se mi líbí

Poměrně dobře podporuje konverzi výjimek na SOAP fault zprávy. Na druhou stranu nefunguje přenastavení jména XML elementu, na který se výjimka konvertuje...

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

Je vcelku jednoduchý a přímočarý. Jediný "problém" je nejspíš (alespoň pro Javu) v nalezení dobrého klienta. Možností je několik:
* TODO


