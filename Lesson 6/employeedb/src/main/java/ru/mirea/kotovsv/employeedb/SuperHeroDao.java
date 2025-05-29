package ru.mirea.kotovsv.employeedb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SuperHeroDao {
    @Query("SELECT * FROM superhero")
    List<SuperHero> getAll();
    @Query("SELECT * FROM superhero WHERE id = :id")
    SuperHero getById(long id);
    @Insert
    void insert(SuperHero employee);
    @Update
    void update(SuperHero employee);
    @Delete
    void delete(SuperHero employee);
}