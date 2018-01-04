package com.beehyv.wareporting.dao;

import com.beehyv.wareporting.model.ModificationTracker;
import com.beehyv.wareporting.model.User;

import java.sql.Date;
import java.util.List;

/**
 * Created by beehyv on 15/3/17.
 */
public interface ModificationTrackerDao {
    ModificationTracker findModificationById(Integer modificationId);

    void saveModification(ModificationTracker modification);

    void deleteModification(ModificationTracker modification);

    List<ModificationTracker> getAllModifications();

    List<ModificationTracker> getAllModificationsByUser(User userId);

    List<ModificationTracker> getAllModifiersForUser(User userId);

    List<ModificationTracker> getAllModificationsByDate(Date date);
}
