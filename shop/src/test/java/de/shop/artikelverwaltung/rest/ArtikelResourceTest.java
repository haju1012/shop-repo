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
import java.text.DateFormat;
import java.util.Date;
import java.util.logging.Logger;

import javax.ws.rs.core.Response;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.shop.artikelverwaltung.domain.Artikel;

import de.shop.util.AbstractResourceTest;

@RunWith(Arquillian.class)
public class ArtikelResourceTest extends AbstractResourceTest {
	private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
	
	private static final Long ARTIKEL_ID_VORHANDEN = Long.valueOf(300);
	private static final Long ARTIKEL_ID_UPDATE = Long.valueOf(301);
	private static final int NEUE_VERSION = 0;
	private static final String NEUE_BEZEICHNUNG = "BezeichnungTest";
	private static final boolean NEU_AUSGESONDERT = false;
	static DateFormat formatter = DateFormat.getDateInstance();
	
		private static Date d = new Date();
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
	@InSequence(5)
	public void createArtikel() {
		LOGGER.finer("BEGINN");
		
		// Given

		final int version = NEUE_VERSION;
		final String bezeichnung = NEUE_BEZEICHNUNG;
		final boolean ausgesondert = NEU_AUSGESONDERT;
		final Date erzeugt = NEU_ERZEUGT;
		final Date aktualisiert = NEU_AKTUALISIERT;
		
		final Artikel artikel = new Artikel();

		artikel.setVersion(version);
		artikel.setBezeichnung(bezeichnung);
		artikel.setAusgesondert(ausgesondert);
		artikel.setErzeugt(erzeugt);
		artikel.setAktualisiert(aktualisiert);

		
		// When
		Long id;
		Response response = getHttpsClient(USERNAME, PASSWORD).target(ARTIKEL_URI)
                                                              .request()
                                                              .post(json(artikel));
			
		// Then
		assertThat(response.getStatus()).isEqualTo(HTTP_CREATED);
		final String location = response.getLocation().toString();
		response.close();
			
		final int startPos = location.lastIndexOf('/');
		final String idStr = location.substring(startPos + 1);
		id = Long.valueOf(idStr);
		assertThat(id).isPositive();
		
		// Gibt es die neue Bestellung?
		response = getHttpsClient().target(ARTIKEL_ID_URI)
                                   .resolveTemplate(ARTIKEL_ID_PATH_PARAM, id)
                                   .request()
                                   .accept(APPLICATION_JSON)
                                   .get();
		assertThat(response.getStatus()).isEqualTo(HTTP_OK);
		response.close();
		
		LOGGER.finer("ENDE");
	}
	
	@Test
	@InSequence(6)
	public void updateArtikel() {
		LOGGER.finer("BEGINN");
		
		// Given
		final Long artikelId = ARTIKEL_ID_UPDATE;
		final String bezeichnung = NEUE_BEZEICHNUNG;

		
		// When
		Response response = getHttpsClient().target(ARTIKEL_ID_URI)
                                            .resolveTemplate(ArtikelResource.ARTIKEL_ID_PATH_PARAM, artikelId)
                                            .request()
                                            .accept(APPLICATION_JSON)
                                            .get();
		Artikel artikel = response.readEntity(Artikel.class);
		assertThat(artikel.getId()).isEqualTo(artikelId);
		final int origVersion = artikel.getVersion();
    	
    	// Aus den gelesenen JSON-Werten ein neues JSON-Objekt mit neuer Bezeichnung bauen
		artikel.setBezeichnung(bezeichnung);
    	
		response = getHttpsClient(USERNAME, PASSWORD).target(ARTIKEL_URI)
                                                     .request()
                                                     .accept(APPLICATION_JSON)
                                                     .put(json(artikel));
		// Then
		assertThat(response.getStatus()).isEqualTo(HTTP_OK);
		artikel = response.readEntity(Artikel.class);
		assertThat(artikel.getVersion()).isGreaterThanOrEqualTo(origVersion);
		
		// Erneutes Update funktioniert, da die Versionsnr. aktualisiert ist
		response = getHttpsClient(USERNAME, PASSWORD).target(ARTIKEL_URI)
                                                     .request()
                                                     .put(json(artikel));
		assertThat(response.getStatus()).isEqualTo(HTTP_OK);
		response.close();
		
		// Erneutes Update funktioniert NICHT, da die Versionsnr. NICHT aktualisiert ist
		response = getHttpsClient(USERNAME, PASSWORD).target(ARTIKEL_URI)
                                                     .request()
                                                     .put(json(artikel));
		assertThat(response.getStatus()).isEqualTo(HTTP_OK);
		response.close();
		
		LOGGER.finer("ENDE");
   	}
	
}
