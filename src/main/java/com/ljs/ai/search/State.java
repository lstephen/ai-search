package com.ljs.ai.search;

/**
 *
 * @author lstephen
 */
public interface State {

    Boolean isValid();

    Number score();

}
