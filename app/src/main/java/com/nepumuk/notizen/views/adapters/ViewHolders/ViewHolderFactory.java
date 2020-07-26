package com.nepumuk.notizen.views.adapters.ViewHolders;

import android.view.View;

import com.nepumuk.notizen.R;
import com.nepumuk.notizen.Utils.DBAccess.DatabaseStorable;
import com.nepumuk.notizen.objects.Notes.cTaskNote;
import com.nepumuk.notizen.objects.Notes.cTextNote;
import com.nepumuk.notizen.objects.Task.cBaseTask;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/**
 * Factory for creating view holders based on the given layout ID.
 * <br>
 * <br>
 * layout IDs must be registered with the correct view holder
 * class and the object class that is being displayed with the view holder
 * <br>
 * for that see {@link ViewHolderFactory#registerNewViewHolder}
 * <br>
 */
public class ViewHolderFactory {

    private static ViewHolderFactory instance;

    private HashMap<Integer , Class<? extends ViewHolderInterface>> typeToViewHolder;
    private HashMap<Class<? extends DatabaseStorable>, Integer > classToType;

    /**
     * a type unknown to the factory is represented by this constant
     */
    public static final int UNKNOWN_TYPE = -1;

    private ViewHolderFactory() {
        super();
        typeToViewHolder = new HashMap<>();
        classToType = new HashMap<>();
    }

    /**
     * get the instance of the view holder factory
     * <br>
     * <br>
     * if a new type of view holder needs to be added, add a call to {@link ViewHolderFactory#registerNewViewHolder} after creation of the factory
     * @return instance of factory
     */
    private static ViewHolderFactory getInstance(){
        if (instance == null){
            instance = new ViewHolderFactory();
            // register new View Types here
            instance.registerNewViewHolder(R.layout.task_view, cBaseTask.class, TaskViewHolder.class);
            instance.registerNewViewHolder(R.layout.note_view, cTextNote.class, TextNoteViewHolder.class);
            instance.registerNewViewHolder(R.layout.task_note_view, cTaskNote.class, TaskNoteViewHolder.class);
        }
        return instance;
    }

    /**
     * get a view holder based on the given type
     *
     * @param type ID of the ViewHolder to be returned
     * @param view that shall be used for creation of ViewHolder
     * @return created ViewHolder
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    private ViewHolderInterface getViewHolder(Integer type, View view) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (typeToViewHolder.containsKey(type) ) {
            return typeToViewHolder.get(type).getConstructor(View.class).newInstance(view);
        }
        throw new IllegalArgumentException("Unknown Type ID given");
    }

    /**
     * register a new view holder and object type with the given type ID
     * <br>
     * <br>
     * the parameter type shall be the layout ID for the view that the view holder will represent
     *
     * @param type ResourceID of the view type that shall be used
     * @param objectType class of the object, that will be represented by the view
     * @param ViewHolderClass class of the view holder that will be holding the references to the view
     */
    private void registerNewViewHolder(Integer type, Class<? extends DatabaseStorable> objectType, Class<? extends ViewHolderInterface> ViewHolderClass){
        if (typeToViewHolder.containsKey(type)){
            throw new IllegalArgumentException("Already Contains this key");
        }
        typeToViewHolder.put(type, ViewHolderClass);
        classToType.put(objectType,type);
    }

    /**
     * get the viewHolderType based on the class that we are requesting a new ViewHolder for
     * <br>
     * <br>
     * in case that specific type has no view holder, we try the superclass
     *
     * @param clazz class of the item we want a view for
     * @return type for use in the RecyclerViewAdapter view type
     */
    protected Integer getType(Class clazz){
        // we have nothing for object.class
        if (clazz == Object.class){
            return UNKNOWN_TYPE;
        }
        if (classToType.containsKey(clazz)){
            return classToType.get(clazz);
        }
        // get superclass view type if possible
        return getType(clazz.getSuperclass());
    }

    /**
     *
     * Gets a new ViewHolder instance based on the type. <br>
     * The given View will be used for creation of the view holder
     * <br>
     * <br>
     * See {@link ViewHolderFactory#registerNewViewHolder} for registration of new types.
     *
     * @param type id (resource ID) of the view
     * @param view the ViewHolder shall be based on
     * @return ViewHolder based on id and view
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     */
    public static ViewHolderInterface getNewViewHolderInstance(Integer type, View view) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return getInstance().getViewHolder(type, view);
    }

    /**
     * gets the type of the view (resource ID) to be represented based on the given class
     * <br/>
     * <br/>
     * in case that specific type has no view holder, the superclass of the given class is tried.
     * <br/>
     * this will be done recursively until {@link Object} is reached, then {@link ViewHolderFactory#UNKNOWN_TYPE} will be returned
     *
     * @param clazz the view shall represent
     * @return id (resource ID) of the view representing the given class
     */
    public static Integer getTypeForClass(Class clazz){
        return getInstance().getType(clazz);
    }



}
