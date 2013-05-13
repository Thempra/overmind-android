package net.thempra.overgame;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor3B;
import org.cocos2d.types.ccColor4B;

import android.view.MotionEvent;

public class GameOverLayer extends CCColorLayer
{
	protected CCLabel _label;
	
	public static CCScene scene(String message)
	{
		CCSprite playerWin ;
		CCScene scene = CCScene.node();
		CGSize winSize = CCDirector.sharedDirector().displaySize();
		GameOverLayer txtLayer = new GameOverLayer(ccColor4B.ccc4(255, 255, 255, 255));
		
		txtLayer.getLabel().setString(message);
		
		CCSprite bckgImage = CCSprite .sprite("game_background.jpg");
		
		if (message.contains("Player1"))
			playerWin= CCSprite.sprite("eva01.png");
		else
			playerWin= CCSprite.sprite("sachiel.png");
		
		bckgImage.setScaleX( winSize.width /  bckgImage.getContentSize().width);
		bckgImage.setScaleY( winSize.height /  bckgImage.getContentSize().height);
		bckgImage.setPosition(CGPoint.ccp(winSize.width / 2.0f, winSize.height / 2.0f));
		playerWin.setPosition(CGPoint.ccp(winSize.width / 2.0f, winSize.height / 2.0f));
		playerWin.setScale(2.0f);
		
		scene.addChild(txtLayer);
		
		scene.addChild(bckgImage);
		scene.addChild(playerWin);
		
		
		
		return scene;
	}
	
	public CCLabel getLabel()
	{
		return _label;
	}
	
	protected GameOverLayer(ccColor4B color)
	{
		super(color);
		
		this.setIsTouchEnabled(true);
		
		CGSize winSize = CCDirector.sharedDirector().displaySize();
		
		_label = CCLabel.makeLabel("Won't See Me", "DroidSans", 70);
		_label.setColor(ccColor3B.ccWHITE);
		_label.setPosition(winSize.width / 2.0f, winSize.height / 2.0f);
		addChild(_label);
		
		this.runAction(CCSequence.actions(CCDelayTime.action(3.0f), CCCallFunc.action(this, "gameOverDone")));
	}
	
	public void gameOverDone()
	{
		CCDirector.sharedDirector().replaceScene(GameLayer.scene());
		
	}
	
	@Override
	public boolean ccTouchesEnded(MotionEvent event)
	{
		gameOverDone();
		
		return true;
	}
}
