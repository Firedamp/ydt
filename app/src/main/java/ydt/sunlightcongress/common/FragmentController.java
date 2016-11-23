package ydt.sunlightcongress.common;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import ydt.sunlightcongress.fragment.BillsFragment;
import ydt.sunlightcongress.fragment.CommitteesFragment;
import ydt.sunlightcongress.fragment.FavoriteFragment;
import ydt.sunlightcongress.fragment.LegislatorsFragment;

/**
 * Created by Caodongyao on 2016/11/23.
 */

public class FragmentController {
    private FragmentManager mFragmentManager;
    private int mContainerId;
    private Page mCurrentPage;

    public enum Page{
        LEGISLATORS,
        FAVORITE,
        COMMITTEES,
        BILLS
    }

    public FragmentController(FragmentManager manager, int containerId){
        this.mFragmentManager = manager;
        this.mContainerId = containerId;
        this.mCurrentPage = null;
    }

    private Fragment getLoadedFragment(Page page){
        if(page == null)
            return null;
        return mFragmentManager.findFragmentByTag(page.name());
    }

    private Fragment createFragment(Page page){
        switch (page){
            default:
            case LEGISLATORS:
                return new LegislatorsFragment();
            case FAVORITE:
                return new FavoriteFragment();
            case COMMITTEES:
                return new CommitteesFragment();
            case BILLS:
                return new BillsFragment();
        }
    }

    public void go(Page page){
        if(page == mCurrentPage)
            return;

        FragmentTransaction transaction = mFragmentManager.beginTransaction();

        Fragment fragment = getLoadedFragment(mCurrentPage);
        if(fragment != null)
            transaction.hide(fragment);

        fragment = getLoadedFragment(page);
        if (fragment != null){
            transaction.show(fragment);
        }
        else{
            transaction.add(mContainerId, createFragment(page), page.name());
        }

        transaction.commit();
        mCurrentPage = page;
    }
}
