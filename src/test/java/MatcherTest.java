
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.peyote.matcher.Matcher.is;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class MatcherTest {

    private List<String> expected;

    @Before
    public void setUp() {
        expected = Arrays.asList("String1", "String2");
    }

    @Test
    public void matcherTest() {
        assertThat(Arrays.asList("String1", "String2"), is(expected));
    }

    @Test
    public void matcherFailTest() {
        try {
            assertThat(Arrays.asList("String", "String2"), is(expected));
        } catch (AssertionError error) {
            assertEquals(error.getMessage(), "\nExpected: Iterator should contain <[String1, String2]>\n"
                    + "     but: was<[String, String2]>");
        }
    }
}