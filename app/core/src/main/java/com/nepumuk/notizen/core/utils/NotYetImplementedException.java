package com.nepumuk.notizen.core.utils;


/**
 * copied from
 * https://stackoverflow.com/questions/2329358/is-there-anything-like-nets-notimplementedexception-in-java
 */
public class NotYetImplementedException extends RuntimeException
{
    /**
     * @deprecated Deprecated to remind you to implement the corresponding code
     *             before releasing the software.
     */
    @Deprecated
    public NotYetImplementedException() {
        super();
    }

    /**
     * @deprecated Deprecated to remind you to implement the corresponding code
     *             before releasing the software.
     */
    @Deprecated
    public NotYetImplementedException(String message)
    {
        super(message);
    }
}