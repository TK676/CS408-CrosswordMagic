package edu.jsu.mcis.cs408.project2.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import java.util.ArrayList;
import edu.jsu.mcis.cs408.project2.R;

public class TabLayoutContainer extends Fragment {
    private TabLayoutAdapter tabLayoutAdapter;
    private PuzzleFragment puzzleFragment;
    private ClueFragment clueFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_container, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        puzzleFragment = new PuzzleFragment(getResources().getString(R.string.tab_title_puzzle));
        clueFragment = new ClueFragment(getResources().getString(R.string.tab_title_clues));

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(puzzleFragment);
        fragments.add(clueFragment);

        tabLayoutAdapter = new TabLayoutAdapter(this, fragments);

        ViewPager2 viewPager = view.findViewById(R.id.pager);
        viewPager.setAdapter(tabLayoutAdapter);

        TabLayout tabLayout = view.findViewById(R.id.tab_layout);

        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText( ((TabFragment)tabLayoutAdapter.createFragment(position)).getTabTitle() );
            }
        }).attach();
    }
}