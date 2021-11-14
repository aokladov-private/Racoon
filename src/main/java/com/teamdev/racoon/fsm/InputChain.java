package com.teamdev.racoon.fsm;

import com.google.common.base.Preconditions;

public class InputChain {

    private final String inputSource;

    private int readingPosition;

    public InputChain(String inputSource) {

        this.inputSource = Preconditions.checkNotNull(inputSource);
    }

    public boolean hasMoreChars() {

        return readingPosition < inputSource.length();
    }

    public char currentChar() {

        return inputSource.charAt(readingPosition);
    }

    public void incrementReadingPosition() {

        readingPosition++;
    }

    public int readingPosition() {

        return readingPosition;
    }

    public void setReadingPosition(int readingPosition) {

        this.readingPosition = readingPosition;
    }

    @Override
    public String toString() {

        return inputSource.substring(readingPosition);
    }
}
