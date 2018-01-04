package com.beehyv.wareporting.business;

import com.beehyv.wareporting.model.ModificationTracker;

import java.sql.Date;
import java.util.List;

/**
 * Created by beehyv on 15/3/17.
 */
public interface ModificationTrackerService {
    public ModificationTracker findModificationById(Integer modificationId);

    public void saveModification(ModificationTracker modification);

    public void deleteModification(ModificationTracker modification);

    public List<ModificationTracker> getAllModifications();

    public List<ModificationTracker> getAllModificationsMadeByUser(Integer userId);

    public List<ModificationTracker> getAllModificationsByDate(Date date);

    public List<ModificationTracker> getAllModifiersForUser(Integer userId);
}

