package com.vgdc.merge.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.Array;


public class AnimationLoader extends AsynchronousAssetLoader<Animation, AnimationLoader.AnimationParameter> {

    public AnimationLoader(FileHandleResolver resolver) {
	super(resolver);
    }

    static public class AnimationParameter extends AssetLoaderParameters<Animation> {
	String sheet;
	
    }

    @Override
    public void loadAsync(AssetManager manager, String fileName, AnimationParameter parameter) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public Animation loadSync(AssetManager manager, String fileName, AnimationParameter parameter) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, AnimationParameter parameter) {
	// TODO Auto-generated method stub
	return null;
    }
}
