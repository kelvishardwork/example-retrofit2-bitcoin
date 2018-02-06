package com.brakeel.exampleretrofit2bitcoin.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.brakeel.exampleretrofit2bitcoin.entity.Ticker;
import com.brakeel.exampleretrofit2bitcoin.entity.Trade;
import com.brakeel.exampleretrofit2bitcoin.util.Constants;

import java.util.ArrayList;

/**
 * Classe responsável pela criação do banco, suas tabelas e persistência de dados
 */
public class BTCRepository extends SQLiteOpenHelper {

    //private static final int ID = 1;

    public BTCRepository(Context context) {
        super(context, Constants.BD_NOME, null, Constants.BD_VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Query de Criação de Tabela TB_TICKERS
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

        // Query de Criação de Tabela TB_TRADES
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

    /**
     * Inserção de dados de Tickers
     *
     * @param ticker Faz o mapeamento a partir desse objeto no banco
     */
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

    /**
     * Limpa a tabela TB_TICKERS pra poder inserir novos dados
     */
    public void truncateTicker() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from TB_TICKERS");
    }

    /**
     * Buscar o único Ticker
     *
     * @return um Objeto Ticker
     */
    public Ticker getTicker() {
        Ticker ticker = new Ticker();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("TB_TICKERS", null, "ID_TICKER > ?", new String[]{String.valueOf(1)}, null, null, "date" + " DESC");
        if (cursor.moveToNext()) {
            setTickerFromCursor(cursor, ticker);
        }
        return ticker;
    }

    /**
     * Faz a recuperação de dados da tabela para o objeto Ticker
     *
     * @param cursor Requer o cursor para poder mapear as tuplas
     * @param ticker Objeto para mapear fazendo um "casting" para se obter um Objeto Ticker
     */
    private void setTickerFromCursor(Cursor cursor, Ticker ticker) {
        ticker.setHigh(cursor.getDouble(cursor.getColumnIndex("HIGH")));
        ticker.setLow(cursor.getDouble(cursor.getColumnIndex("LOW")));
        ticker.setVol(cursor.getDouble(cursor.getColumnIndex("VOL")));
        ticker.setLast(cursor.getDouble(cursor.getColumnIndex("LAST")));
        ticker.setBuy(cursor.getDouble(cursor.getColumnIndex("BUY")));
        ticker.setSell(cursor.getDouble(cursor.getColumnIndex("SELL")));
        ticker.setDate(cursor.getInt(cursor.getColumnIndex("DATE")));
    }

    /**
     * Inserção de dados de Trade
     * @param trade Faz o mapeamento a partir desse objeto no banco
     */
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

    /**
     * Limpa a tabela TB_TRADES pra poder inserir novos dados
     */
    public void truncateTrade() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM TB_TRADES");
    }

    /**
     * Buscar todos os registros da tabela Trades
     * @return Retorna uma lista de Trades, ordenadas por data em modo descrecente
     */
    public ArrayList<Trade> getAllTrades() {
        ArrayList<Trade> listTrade = new ArrayList<Trade>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("TB_TRADES", null, null, null, null, null, "date" + " DESC");
        while (cursor.moveToNext()) {
            Trade trade = new Trade();
            setTradeFromCursor(cursor, trade);
            listTrade.add(trade);
        }
        return listTrade;
    }

    /**
     * Faz a recuperação de dados da tabela para o objeto Trade
     * @param cursor Requer o cursor para poder mapear as tuplas
     * @param trade Objeto para mapear fazendo um "casting" para se obter um Objeto Trade
     */
    private void setTradeFromCursor(Cursor cursor, Trade trade) {
        trade.setDate(cursor.getInt(cursor.getColumnIndex("DATE")));
        trade.setPrice(cursor.getDouble(cursor.getColumnIndex("PRICE")));
        trade.setAmount(cursor.getDouble(cursor.getColumnIndex("AMOUNT")));
        trade.setTid(cursor.getInt(cursor.getColumnIndex("TID")));
        trade.setType(cursor.getString(cursor.getColumnIndex("TYPE")));
    }


}
