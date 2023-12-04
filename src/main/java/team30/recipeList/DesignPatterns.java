package team30.recipeList;

interface Subject {
	void register(Observer o);
	void notifyObservers();
}
interface Observer {
	void update();
}
