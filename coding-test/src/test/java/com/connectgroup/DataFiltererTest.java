package com.connectgroup;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;

import org.junit.Test;

public class DataFiltererTest {
	@Test
    public void shouldReturnEmptyCollection_WhenLogFileIsEmpty() throws IOException {
        assertTrue(DataFilterer.filterByCountry(openFile("src/test/resources/empty"), "GB").isEmpty());
    }
	
	@Test
    public void filterByCountry_matchFound() throws IOException {
		Collection<?> result = DataFilterer.filterByCountry(openFile("src/test/resources/multi-lines"), "GB");
        assertFalse(result.isEmpty());
        assertEquals(result.size(), 1);
    }
	
	@Test
    public void filterByCountry_matchNotFound() throws IOException {
        assertTrue(DataFilterer.filterByCountry(openFile("src/test/resources/single-line"), "US").isEmpty());
    }
	
	@Test
    public void filterByCountryWithResponseTimeAboveLimit_EmptyFile() throws IOException {
        assertTrue(DataFilterer.filterByCountryWithResponseTimeAboveLimit(openFile("src/test/resources/empty"), "GB", 100).isEmpty());
    }
	
	@Test
    public void filterByCountryWithResponseTimeAboveLimit_matchFound() throws IOException {
        Collection<?> result = DataFilterer.filterByCountryWithResponseTimeAboveLimit(openFile("src/test/resources/multi-lines"), "US", 100);
        assertFalse(result.isEmpty());
        assertEquals(result.size(), 3);
    }
	
	@Test
    public void filterByCountryWithResponseTimeAboveLimit_matchNotFound() throws IOException {
        assertTrue(DataFilterer.filterByCountryWithResponseTimeAboveLimit(openFile("src/test/resources/single-line"), "GB", 200).isEmpty());
    }
	
	public void filterByResponseTimeAboveAverage_EmptyFile() throws IOException {
        assertTrue(DataFilterer.filterByResponseTimeAboveAverage(openFile("src/test/resources/empty")).isEmpty());
    }
	
	@Test
    public void filterByResponseTimeAboveAverage_matchFound() throws IOException {
        Collection<?> result = DataFilterer.filterByResponseTimeAboveAverage(openFile("src/test/resources/multi-lines"));
        assertFalse(result.isEmpty());
        assertEquals(result.size(), 4);
    }
	
	@Test
    public void ffilterByResponseTimeAboveAverage_matchNotFound() throws IOException {
		Collection<?> result = DataFilterer.filterByResponseTimeAboveAverage(openFile("src/test/resources/single-line"));
        assertFalse(result.isEmpty());
        assertEquals(result.size(), 1);
    }

    private FileReader openFile(String filename) throws FileNotFoundException {
        return new FileReader(new File(filename));
    }
}
