package bombercraft.game.level;

import java.util.ArrayList;
import java.util.function.Predicate;

import core.Interactable;
import utils.GVector2f;

public interface MapAble<T> extends Interactable{
	public T getBlock(int i, int j);
	public GVector2f getNumberOfBlock();
	public T getRandomBlock(Predicate<? super T> condition);
	public ArrayList<T> getNeightboards(boolean diagonal);
	public GVector2f getSize();
}
