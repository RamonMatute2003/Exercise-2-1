package com.example.exercise_2_1.Settings;

public class Transactions {
    public static final String name_database="PMO1_Exercise2_1";
    public static final String tableVideo="videos";
    public static final String columnVideo="video";

    public static final String create_table_videos="CREATE TABLE videos(id INTEGER PRIMARY KEY AUTOINCREMENT, video BLOB)";
    public static final String drop_table_videos="DROP TABLE IF EXISTS videos";

}
