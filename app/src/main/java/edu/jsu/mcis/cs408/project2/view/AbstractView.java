package edu.jsu.mcis.cs408.project2.view;

import java.beans.PropertyChangeEvent;

public interface AbstractView {
    public abstract void modelPropertyChange(final PropertyChangeEvent evt);
}
