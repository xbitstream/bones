package pro.horovodovodo4ka.bones.sample.navigation

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_tab_bar.*
import pro.horovodovodo4ka.bones.Bone
import pro.horovodovodo4ka.bones.BoneStateValue
import pro.horovodovodo4ka.bones.Finger
import pro.horovodovodo4ka.bones.Primacy
import pro.horovodovodo4ka.bones.Wrist
import pro.horovodovodo4ka.bones.persistance.BonePersisterInterface
import pro.horovodovodo4ka.bones.sample.R
import pro.horovodovodo4ka.bones.ui.WristNavigatorInterface
import pro.horovodovodo4ka.bones.ui.delegates.WristNavigator
import pro.horovodovodo4ka.bones.ui.extensions.indexOf

class TabBar(vararg finger: Bone) : Wrist(*finger) {
    override val seed = { TabBarFragment() }
}

class TabBarFragment : Fragment(),
    BonePersisterInterface<TabBar>,
    WristNavigatorInterface<TabBar> by WristNavigator(R.id.wrist_container, true) {

    // region ContainerFragmentSibling

    override fun onAttach(context: Context) {
        super.onAttach(context)
        managerProvider = ::getChildFragmentManager
    }

    override fun onDetach() {
        super.onDetach()
        managerProvider = null
    }

    // endregion

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_tab_bar, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(navigation_bar) {
            selectedItemId = menu.getItem(bone.activeBoneIndex).itemId

            setOnNavigationItemSelectedListener { item ->
                val oldIdx = bone.activeBoneIndex

                bone.activeBoneIndex = menu.indexOf(item)

                if (oldIdx == bone.activeBoneIndex) (bone.activeBone as? Finger)?.popToRoot()
                true
            }
        }

        refreshUI()
    }

    // region BonePersisterInterface

    override fun onSaveInstanceState(outState: Bundle) {
        super<BonePersisterInterface>.onSaveInstanceState(outState)
        super<Fragment>.onSaveInstanceState(outState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super<BonePersisterInterface>.onCreate(savedInstanceState)
        super<Fragment>.onCreate(savedInstanceState)
    }

    // endregion

    override fun onBoneStateChange(state: BoneStateValue) {
        if (state is Primacy) Log.d("primacy", "wrist   ${bone.id.substring(0..7)} -> ${bone.isPrimary}")
    }
}
