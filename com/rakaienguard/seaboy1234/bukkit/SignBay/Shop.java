package com.rakaienguard.seaboy1234.bukkit.SignBay;

import javax.persistence.*;

import com.avaje.ebean.validation.NotNull;

@Entity()
@Table(name = "sign_shops")
public class Shop {
	@Id
	private int Id;
	@NotNull
	private int itemId;
	@NotNull
	private int price;
	@NotNull
	private String curName;
	@NotNull
	private int x,y,z,stock,count,curId,balance;
	@NotNull
	private String world,owner;

	public void setId(int id) {
		Id = id;
	}

	public int getId() {
		return Id;
	}

	/**
	 * @return the itemId
	 */
	public int getItemId() {
		return itemId;
	}

	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	/**
	 * @return the price
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(int price) {
		this.price = price;
	}

	/**
	 * @return the curId
	 */
	public int getCurId() {
		return curId;
	}

	/**
	 * @param curId the curId to set
	 */
	public void setCurId(int curId) {
		this.curId = curId;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @return the z
	 */
	public int getZ() {
		return z;
	}

	/**
	 * @param z the z to set
	 */
	public void setZ(int z) {
		this.z = z;
	}

	/**
	 * @return the world
	 */
	public String getWorld() {
		return world;
	}

	/**
	 * @param world the world to set
	 */
	public void setWorld(String world) {
		this.world = world;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public int getStock() {
		return stock;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getOwner() {
		return owner;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getCount() {
		return count;
	}

	public void setCurName(String curName) {
		this.curName = curName;
	}

	public String getCurName() {
		return curName;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public int getBalance() {
		return balance;
	}
}
