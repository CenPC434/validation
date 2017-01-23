////////////////////////////////////////////////////////////////////////
//
// Parser.java
//
// This file was generated by MapForce 2017sp2.
//
// YOU SHOULD NOT MODIFY THIS FILE, BECAUSE IT WILL BE
// OVERWRITTEN WHEN YOU RE-RUN CODE GENERATION.
//
// Refer to the MapForce Documentation for further details.
// http://www.altova.com/mapforce
//
////////////////////////////////////////////////////////////////////////

package com.altova.text.tablelike.csv;

import com.altova.text.tablelike.RecordBasedParser;
import com.altova.text.tablelike.StringList;

public class Parser extends RecordBasedParser {
    private Format m_Format = null;

    private String m_Buffer = "";

    private int m_CurrentOffset = 0;

    private ParserState m_CurrentState = null;

    private ParserStateFactory m_States = null;

    private String m_Token = "";

    private StringList m_Fields = new StringList();

    private int m_FirstRecordFieldCount = 0;

    private int m_RecordCount = 0;

    boolean isEndOfBuffer() {
        return (m_CurrentOffset >= m_Buffer.length());
    }

    boolean wasLastCharacterFieldDelimiter() {
        if (0 < m_CurrentOffset)
            return m_Format.isFieldDelimiter(m_Buffer
                    .charAt(m_CurrentOffset - 1));
        else
            return false;
    }

    boolean wasLastCharacterQuote() {
        if (0 < m_CurrentOffset)
            return m_Format.isQuoteCharacter(m_Buffer
                    .charAt(m_CurrentOffset - 1));
        else
            return false;
    }

    char getCurrentCharacter() {
        return m_Buffer.charAt(m_CurrentOffset);
    }

    void moveNext() {
        ++m_CurrentOffset;
    }

    String getToken() {
        return m_Token;
    }

    void setToken(String rhs) {
        m_Token = rhs;
    }

    void appendCharacterToToken(char rhs) {
        m_Token += rhs;
    }

	private boolean hasAnyFieldsWithContent() {
		for ( int i = 0; i < m_Fields.size(); ++i ) {
			String f = m_Fields.getAt( i );
			if ( f != null && f.length() > 0 )
				return true;
		}
		return false;
	}

    void notifyAboutEndOfRecord() throws BadFormatException {
        if (0 < m_Fields.size()) {
			if ( hasAnyFieldsWithContent() ) {
                if (0 == m_RecordCount)
                    m_FirstRecordFieldCount = m_Fields.size();
                ++m_RecordCount;
                super.notifyAboutRecordFound(m_Fields);
            }
            m_Fields.clear();
        }
    }

    void notifyAboutTokenComplete() {
		if (m_Format.getRemoveEmpty() && m_Token.length() == 0)
			m_Token = null;
        m_Fields.add(m_Token);
        m_Token = "";
    }

    public Parser(Format format) {
        m_Format = format;
        m_States = new ParserStateFactory(this);
    }

    public Format getFormat() {
        return m_Format;
    }

    public int parse(String buffer) throws ParserException {
        m_Buffer = buffer;
        m_CurrentOffset = 0;
        m_Token = "";
        m_FirstRecordFieldCount = 0;
        m_RecordCount = 0;
        m_CurrentState = m_States.getWaitingForField();
        try {
            while (!this.isEndOfBuffer()) {
                char current = this.getCurrentCharacter();
                if (m_Format.isFieldDelimiter(current))
                    m_CurrentState = m_CurrentState
                            .processFieldDelimiter(current);
                else if (m_Format.isRecordDelimiter(current))
                    m_CurrentState = m_CurrentState
                            .processRecordDelimiter(current);
                else if (m_Format.isQuoteCharacter(current))
                    m_CurrentState = m_CurrentState
                            .processQuoteCharacter(current);
                else
                    m_CurrentState = m_CurrentState.process(current);
            }
            if (0 < m_Token.length())
                this.notifyAboutTokenComplete();
            this.notifyAboutEndOfRecord();
        } catch (BadFormatException x) {
            throw new ParserException(x, m_RecordCount);
        }
        return m_RecordCount;
    }

}
