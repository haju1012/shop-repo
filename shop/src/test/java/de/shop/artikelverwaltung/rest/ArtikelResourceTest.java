package de.shop.artikelverwaltung.rest;

import static de.shop.util.TestConstants.ARTIKEL_ID_PATH_PARAM;
import static de.shop.util.TestConstants.ARTIKEL_ID_URI;
import static de.shop.util.TestConstants.ARTIKEL_URI;
import static de.shop.util.TestConstants.PASSWORD;
import static de.shop.util.TestConstants.USERNAME;
import static java.net.HttpURLConnection.HTTP_CREATED;
import static java.net.HttpURLConnection.HTTP_OK;
import static javax.ws.rs.client.Entity.json;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.fail;

import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.util.Date;
import java.util.logging.Logger;

import javax.mail.internet.ParseException;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.shop.artikelverwaltung.domain.Artikel;
import de.shop.bestellverwaltung.domain.Bestellposition;
import de.shop.bestellverwaltung.domain.Bestellung;
import de.shop.kundenverwaltung.domain.Adresse;
import de.shop.kundenverwaltung.domain.Privatkunde;
import de.shop.util.AbstractResourceTest;

@RunWith(Arquillian.class)
public class ArtikelResourceTest extends AbstractResourceTest {
	private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
	
	private static final Long ARTIKEL_ID_VORHANDEN = Long.valueOf(300);
	private static final int NEUE_VERSION = 0;
	private static final String NEUE_BEZEICHNUNG ="BezeichnungTest";
	private static final boolean NEU_AUSGESONDERT = false;
	static DateFormat formatter =DateFormat.getDateInstance();
	
		private static Date d=new Date();
		private static final Date NEU_ERZEUGT = d;
		private static final Date NEU_AKTUALISIERT = d;
		

	
	@Test
	@InSequence(1)
	public void validate() {
		assertThat(true).isTrue();
	}
	
	@Ignore
	@Test
	@InSequence(2)
	public void beispielIgnore() {
		assertThat(true).isFalse();
	}
	
	@Ignore
	@Test
	@InSequence(3)
	public void beispielFailMitIgnore() {
		fail("Test Fail Message soll failen");
	}
	
	@Test
	@InSequence(4)
	public void findArtikelById() {
		LOGGER.finer("BEGINN");
		
		// Given
		final Long artikelId = ARTIKEL_ID_VORHANDEN;
		
		// When
		final Response response = getHttpsClient().target(ARTIKEL_ID_URI)
		                                                  .resolveTemplate(ARTIKEL_ID_PATH_PARAM, artikelId)
		                                                  .request()
		                                                  .accept(APPLICATION_JSON)
		                                                  .get();
	
		// Then
		assertThat(response.getStatus()).isEqualTo(HTTP_OK);
		final Artikel artikel = response.readEntity(Artikel.class);
				
		assertThat(artikel.getId()).isEqualTo(artikelId);
		// assertThat(artikel.getBestellpositionen()).isNotEmpty();

		LOGGER.finer("ENDE");
	}
	
	@Test
	@InSequence(10)
	public void createArtikel() throws URISyntaxException {
		LOGGER.finer("BEGINN");
		
		// Given
		final Long artikelId = ARTIKEL_ID_VORHANDEN;
		final int version = NEUE_VERSION;
		final String bezeichnung = NEUE_BEZEICHNUNG;
		final boolean ausgesondert = NEU_AUSGESONDERT;
		final Date erzeugt = NEU_ERZEUGT;
		final Date aktualisiert = NEU_AKTUALISIERT;
		
		final Artikel artikel = new Artikel(version, bezeichnung, ausgesondert, erzeugt, aktualisiert); //id,
		// artikel.setID(id);
		artikel.setVersion(version);
		artikel.setBezeichnung(bezeichnung);
		artikel.setAusgesondert(ausgesondert);
		artikel.setAktualisiert(aktualisiert);

		
		// Neues, client-seitiges Bestellungsobjekt als JSON-Datensatz
		final Bestellung bestellung = new Bestellung();
		
		Bestellposition bp = new Bestellposition();
		bp.setArtikelUri(new URI(ARTIKEL_URI + "/" + artikelId1));
		bp.setAnzahl((short) 1);
		bestellung.addBestellposition(bp);

		bp = new Bestellposition();
		bp.setArtikelUri(new URI(ARTIKEL_URI + "/" + artikelId2));
		bp.setAnzahl((short) 1);
		bestellung.addBestellposition(bp);
		
		// When
		Long id;
		Response response = getHttpsClient(USERNAME, PASSWORD).target(BESTELLUNGEN_URI)
                                                              .request()
                                                              .post(json(bestellung));
			
		// Then
		assertThat(response.getStatus()).isEqualTo(HTTP_CREATED);
		final String location = response.getLocation().toString();
		response.close();
			
		final int startPos = location.lastIndexOf('/');
		final String idStr = location.substring(startPos + 1);
		id = Long.valueOf(idStr);
		assertThat(id).isPositive();
		
		// Gibt es die neue Bestellung?
		response = getHttpsClient().target(BESTELLUNGEN_ID_URI)
                                   .resolveTemplate(BESTELLUNGEN_ID_PATH_PARAM, id)
                                   .request()
                                   .accept(APPLICATION_JSON)
                                   .get();
		assertThat(response.getStatus()).isEqualTo(HTTP_OK);
		response.close();
		
		LOGGER.finer("ENDE");
	}
	
}
