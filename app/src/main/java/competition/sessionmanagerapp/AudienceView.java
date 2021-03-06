package competition.sessionmanagerapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.TabHost;

public class AudienceView extends Fragment implements OnFragmentInteractionListener, ViewPager.OnPageChangeListener, TabHost.OnTabChangeListener{

    private ViewPager pager;
    private TabHost tabHost;
    private View view;
    private Fragment[] tabs;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public void setContactDetails (String n, String e, String l){

    }

    public AudienceView(String macAddress, String ip, String sessionName, String n, String e, String l) {
        tabs = new Fragment[]{new Chart(macAddress, ip, sessionName), new ContactsView(n, e, l)};
    }

    public AudienceView() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AudienceView.
     */
    // TODO: Rename and change types and number of parameters
    public static AudienceView newInstance(String param1, String param2) {
        AudienceView fragment = new AudienceView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_audience_view, container, false);
        initViewPager();
        initTabHost();
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void initTabHost (){
        tabHost = (TabHost) view.findViewById(R.id.tabHostAudience);
        tabHost.setup();
        String[] tabNames = {"Survey", "Contacts"};
        for (int i = 0; i < tabNames.length; i++){
            TabHost.TabSpec tabSpec;
            tabSpec = tabHost.newTabSpec(tabNames[i]);
            tabSpec.setIndicator(tabNames[i]);
            tabSpec.setContent(new FakeContent(view.getContext()));
            tabHost.addTab(tabSpec);
        }
        tabHost.setOnTabChangedListener(this);
        tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).getBackground().setColorFilter(Color.CYAN, PorterDuff.Mode.MULTIPLY);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        tabHost.setCurrentTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabChanged(String s) {
        int select = tabHost.getCurrentTab();
        pager.setCurrentItem(select);
        HorizontalScrollView horizontalScrollView = (HorizontalScrollView) view.findViewById(R.id.horizontalScrollerAudience);
        View tabView = tabHost.getCurrentTabView();
        int scrollPos = tabView.getLeft() - (horizontalScrollView.getWidth() - tabView.getWidth()) / 2;
        horizontalScrollView.smoothScrollTo(scrollPos, 0);
    }

    public class FakeContent implements TabHost.TabContentFactory{

        Context context;

        public FakeContent (Context c){
            context = c;
        }

        @Override
        public View createTabContent(String s) {
            View fakeView = new View(context);
            fakeView.setMinimumHeight(0);
            fakeView.setMinimumWidth(0);
            return fakeView;
        }
    }

    public void initViewPager(){

        pager = (ViewPager) view.findViewById(R.id.view_pagerAudience);
        pager.setAdapter(new MyFragmentPagerAdapter(getActivity().getSupportFragmentManager(), tabs));
        pager.setOnPageChangeListener(this);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
