package enjoyor.enjoyorzemobilehealth.entities;

/**
 * 
 * @author Administrator
 * 
 */
public class FoodData {
	private int type;// 类型 根据类型显示标题
	private String name;//
	private String title;
	private int child;


	public FoodData(int type, String name, String title, int child ) {
		this.type = type;
		this.name = name;
		this.title = title;
		this.child = child;

	}

	public int getChild() {
		return child;
	}

	public void setChild(int child) {
		this.child = child;
	}
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public FoodData(int type, String name, String title) {
		this.type = type;
		this.name = name;
		this.title = title;
	}
}
