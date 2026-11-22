package interface_adapters;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

// S is type parameter, a placeholder for type that gets filled later
// generic class
public class ViewModel<S> {

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private S state;

    public ViewModel(S initialState) {
        this.state = initialState;
    }

    public S getState() {
        return state;
    }

    public void setState(S newState) {
        S old = this.state;
        this.state = newState;
        support.firePropertyChange("state", old, newState); // calls propertyChange on TaskListView
    }

    // View calls this to be told about state change
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
