package bombercraft.game.entity.helper.bullet;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import bombercraft.game.GameAble;
import utils.GVector2f;
import utils.SpriteAnimation;

public class BulletMagic extends Bullet{
	private SpriteAnimation img = new SpriteAnimation("fireball_red.png", 3, 2, 5);
	public BulletMagic(GVector2f position, GameAble parent, GVector2f direction, int speed, int healt, int demage) {
		super(position, parent, direction, speed, healt, demage, new GVector2f(50,50), Color.green, null);
	}
	
	@Override
	public void render(Graphics2D g2) {
		GVector2f pos = getPosition().mul(getParent().getZoom()).sub(getParent().getOffset());
//		GVector2f pos2 = pos.add(getSize().div(2));
		GVector2f totalSize = getSize().mul(getParent().getZoom());
//		AffineTransform old = g2.getTransform();
//		
//		AffineTransform nova = new AffineTransform();
//		nova.translate(pos.getX(), pos.getYi());
//		nova.rotate(-Math.atan2(getDirection().getX(), getDirection().getY()) - Math.toRadians(90));
//		g2.setTransform(nova);
//		img.renderAndCheckLastFrame(g2, new GVector2f(), new GVector2f(100,40));
//		
//		g2.setTransform(old);
		Color[] colors  = new Color[]{getColor(), new Color(getColor().getRed(), getColor().getGreen(), getColor().getBlue(), 0)};
		Point2D center = new Point2D.Float(pos.getX() + totalSize.getXi() / 2, pos.getY() + totalSize.getYi() / 2);
		g2.setPaint(new RadialGradientPaint(center, totalSize.max() / 2, new float[]{0,1}, colors));
		g2.fillArc(pos.getXi(), pos.getYi(), totalSize.getXi(), totalSize.getYi(), 0, 360);
	}

	@Override
	public String toJSON() {
		return null;
	}

}
