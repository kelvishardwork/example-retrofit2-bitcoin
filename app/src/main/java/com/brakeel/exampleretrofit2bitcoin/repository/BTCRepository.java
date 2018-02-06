package com.brakeel.exampleretrofit2bitcoin.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.brakeel.exampleretrofit2bitcoin.entity.Ticker;
import com.brakeel.exampleretrofit2bitcoin.entity.Trade;
import com.brakeel.exampleretrofit2bitcoin.util.Constants;

/**
 * Created by Kelvis Borges on 05/02/2018.
 */

public class BTCRepository extends SQLiteOpenHelper {

    //private static final int ID = 1;

    public BTCRepository(Context context) {
        super(context, Constants.BD_NOME, null, Constants.BD_VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder query = new StringBuilder();
        query.append("CREATE TABLE IF NOT EXISTS TB_TICKERS( ");
        query.append(" ID_TICKER INTEGER PRIMARY KEY AUTOINCREMENT,");
        query.append(" HIGH REAL NOT NULL,");
        query.append(" LOW REAL NOT NULL,");
        query.append(" VOL REAL NOT NULL,");
        query.append(" LAST REAL NOT NULL,");
        query.append(" BUY REAL NOT NULL,");
        query.append(" SELL REAL NOT NULL,");
        query.append(" DATE INTEGER NOT NULL)");
        db.execSQL(query.toString());
        //popularBD(db);

        query = new StringBuilder();
        query.append("CREATE TABLE IF NOT EXISTS TB_TRADES( ");
        query.append(" ID_TRADE INTEGER PRIMARY KEY AUTOINCREMENT,");
        query.append(" DATE INTEGER NOT NULL,");
        query.append(" PRICE REAL NOT NULL,");
        query.append(" AMOUNT REAL NOT NULL,");
        query.append(" TID INTEGER NOT NULL,");
        query.append(" TYPE VARCHAR(4) NOT NULL)");
        db.execSQL(query.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void addTicker(Ticker ticker) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("HIGH", ticker.getHigh());
        contentValues.put("LOW", ticker.getLow());
        contentValues.put("VOL", ticker.getVol());
        contentValues.put("LAST", ticker.getLast());
        contentValues.put("BUY", ticker.getBuy());
        contentValues.put("SELL", ticker.getSell());
        contentValues.put("DATE", ticker.getDate());
        db.insert("TB_TICKERS", null, contentValues);
    }

    public void addTrade(Trade trade) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("DATE", trade.getDate());
        contentValues.put("PRICE", trade.getPrice());
        contentValues.put("AMOUNT", trade.getAmount());
        contentValues.put("TID", trade.getTid());
        contentValues.put("TYPE", trade.getType());
        db.insert("TB_TRADES", null, contentValues);
    }




}
