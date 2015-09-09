package bombercraft.game.entity;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import bombercraft.game.GameAble;
import core.Interactable;
import utils.GVector2f;

public class Lightning extends Entity{
    private List<Lightning> lines;
    private GVector2f A;
    private GVector2f B;
    private float thickness;
    
    public Lightning(GVector2f a, GVector2f b, float thickness, GameAble parent){
    	this(a, b, thickness, false, parent);
    }
    
    public Lightning(GVector2f a, GVector2f b, float thickness, boolean child, GameAble parent) {
    	super(a, parent);
    	if(child){
    		this.A = a;
    		this.B = b;
    		this.thickness = thickness;
    	}
    	else
    		lines = CreateBolt(a, b, thickness);
    	
	}
	
	@Override
	public void render(Graphics2D g2) {
		alive = false;
		if(lines == null){
			g2.setColor(Color.BLUE);
			g2.setStroke(new BasicStroke(thickness * getParent().getZoom()));
			g2.drawLine(A.getXi(), A.getYi(), B.getXi(), B.getYi());
		}
		else
			lines.stream().forEach(a -> a.render(g2));
	}
	
	protected List<Lightning> CreateBolt(GVector2f source, GVector2f dest, float thickness){
		List<Lightning> results = new ArrayList<Lightning>();
		GVector2f tangent = dest.sub(source);
		GVector2f normal = new GVector2f(tangent.getY(), - tangent.getX()).Normalized();
	    float length = tangent.getLength();
	 
	    List<Float> positions = new ArrayList<Float>();
	    positions.add(0f);
	 
	    for (int i = 0; i < length / 4; i++)
	        positions.add((float)Math.random());
	 
	    Collections.sort(positions);
	    float Sway = 80;
	    float Jaggedness = 1 / Sway;
	 
	    GVector2f prevPoint = source;
	    float prevDisplacement = 0;
	    for (int i = 1; i < positions.size(); i++){
	        float pos = positions.get(i);
	 
	        float scale = (length * Jaggedness) * (pos - positions.get(i - 1));
	 
	        float envelope = pos > 0.95f ? 20 * (1 - pos) : 1;
	 
	        float displacement = (float)(Math.random() - 0.5) * Sway * 2;
	        displacement -= (displacement - prevDisplacement) * (1 - scale);
	        displacement *= envelope;
	 
	        GVector2f point = source.add(tangent.mul(pos)).add(normal.mul(displacement));
	        results.add(new Lightning(prevPoint, point, thickness, true, getParent()));
	        prevPoint = point;
	        prevDisplacement = displacement;
	    }
	 
	    results.add(new Lightning(prevPoint, dest, thickness, true, getParent()));
	 
	    return results;
	}

	@Override
	public String toJSON() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GVector2f getSur() {
		return null;
	}

	@Override
	public GVector2f getSize() {
		return new GVector2f(800,600);
	}
}
