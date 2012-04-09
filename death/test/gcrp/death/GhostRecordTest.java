package gcrp.death;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

public class GhostRecordTest {
	@Test
	public void testCreateNull() {
		GhostRecord r = new GhostRecord();
		assertEquals(r.isNull(), true);
		assertEquals(r.getPlayerName(), null);
		assertEquals(r.getWorldName(), null);
		assertEquals(r.getCorpsePosition(), null);
	}

	@Test
	public void testCreateThenInit() {
		final String playerName = "Player Name";
		GregorianCalendar cal = new GregorianCalendar();
		cal.set(2012, 03, 31, 22, 15, 22);
		final Date pit = cal.getTime();
		
		GhostRecord r = new GhostRecord();
		assertEquals(r.isNull(), true);
		r.setPlayerName(playerName);
		assertEquals(r.isNull(), true);
		assertEquals(r.getPlayerName(), playerName);
		r.setPITDeath(pit);
		assertEquals(r.isNull(), true);
		assertEquals(r.getPITDeath(), pit);		
	}

	
}
