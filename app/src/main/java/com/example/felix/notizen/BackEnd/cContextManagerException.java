package com.example.felix.notizen.BackEnd;


/**
 * exception thrown by cContextManager
 *
 * Created as part of notes in package com.example.felix.notizen.BackEnd
 * by Felix "nepumuk" Wiemann on 04/06/17.
 */
@SuppressWarnings("unused")
public class cContextManagerException extends cNoteException {
    public static String aContextAlreadySet = "<CONTEXT IS ALREADY SET>";


    public cContextManagerException(String location,String message,cNoteException cause){
        super(location,message,cause);
    }


    @Override
    public void raise() throws cContextManagerException {
        logException();
        throw this;
    }
}
