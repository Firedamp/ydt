package ydt.sunlightcongress.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;

/**
 * Created by Caodongyao on 2016/11/23.
 */

public class FragmentController {
    private FragmentManager mFragmentManager;
    private int mContainerId;
    private String mCurrentPageTag;
    private FragmentAdapter mAdapter;

    public FragmentController(@NonNull FragmentManager manager, int containerId, @NonNull FragmentAdapter adapter){
        this.mFragmentManager = manager;
        this.mContainerId = containerId;
        this.mCurrentPageTag = null;
        this.mAdapter = adapter;
    }

    private Fragment getLoadedFragment(String tag){
        if(tag == null)
            return null;
        return mFragmentManager.findFragmentByTag(tag);
    }

    public void go(String tag){
        if(tag == mCurrentPageTag)
            return;

        FragmentTransaction transaction = mFragmentManager.beginTransaction();

        Fragment fragment = getLoadedFragment(mCurrentPageTag);
        if(fragment != null)
            transaction.hide(fragment);

        fragment = getLoadedFragment(tag);
        if (fragment != null){
            transaction.show(fragment);
        }
        else{
            transaction.add(mContainerId, mAdapter.createFragment(tag), tag);
        }

        transaction.commit();
        mCurrentPageTag = tag;
    }

    public interface FragmentAdapter{
        Fragment createFragment(String tag);
    }
}
