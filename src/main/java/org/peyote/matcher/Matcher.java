package org.peyote.matcher;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.ArrayList;
import java.util.List;
/**
 * This Matcher compares Iterable with Iterable for equals.
 *
 * @author Anton Panciuc
 */
public class Matcher extends TypeSafeMatcher<Iterable> {

    private List<Object> expected = new ArrayList<>();

    /**
     * Constructor is used to fill Collection from Iterable
     * @param expected
     */
    public Matcher(Iterable expected) {
        for (Object expects : expected) {
            this.expected.add(expects);
        }
    }

    /**
     * This method does actual comparision
     * @param item
     * @return
     */

    @Override
    public boolean matchesSafely(Iterable item) {
        List<Object> origin = new ArrayList<>();
        for (Object origins : item) {
            origin.add(origins);
        }
        return origin.equals(expected);
    }

    /**
     * This method shows expected input for comparision
     * @param description
     */
    @Override
    public void describeTo(Description description) {
        description.appendText("Iterator should contain ").appendValue(expected);
    }

    /**
     * This method shows origin input in error message
     * @param item
     * @param description
     */
    @Override
    public void describeMismatchSafely(final Iterable item, final
    Description description) {
        List<Object> origin = new ArrayList<>();
        for (Object origins : item) {
            origin.add(origins);
        }
        description.appendText("was").appendValue(origin);
    }

    /**
     * This static method need to be called to run class logic
     * @param expected
     */
    public static Matcher is(Iterable expected) {
        return new Matcher(expected);
    }
}