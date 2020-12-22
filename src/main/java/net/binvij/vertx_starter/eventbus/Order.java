package net.binvij.vertx_starter.eventbus;

public class Order {
	
	private Integer id;
	
	public Order(Integer id) {
		this.id = id;
	}
	
	
	public Integer getId() {
		return id;
	}

	@Override
	public String toString() {
		return id + "";
	}
}
