package tech.okcredit.create_pdf.activity

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import tech.okcredit.create_pdf.R
import tech.okcredit.create_pdf.custom.TouchImageViewFling
import tech.okcredit.create_pdf.custom.ViewPagerForPhotoView
import tech.okcredit.create_pdf.utils.PDFUtil
import java.io.File
import java.util.LinkedList
import java.util.Locale

open class PDFViewerActivity : AppCompatActivity() {
    var pdfFile: File? = null
        private set

    lateinit var composeView: ComposeView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_viewer)
        if (!intent.hasExtra(PDF_FILE_URI)) {
            IllegalStateException("set PdfViewerActivity.PDF_FILE_URI before using PdfViewerActivity").printStackTrace()
            finish()
            return
        }
        val pdfFileUri = intent.getParcelableExtra<Uri>(PDF_FILE_URI)
        if (pdfFileUri == null || pdfFileUri.path == null) {
            IllegalStateException("pdf File Uri is null").printStackTrace()
            finish()
            return
        }
        pdfFile = File(pdfFileUri.path)
        if (!pdfFile!!.exists()) {
            IllegalStateException("File Does Not Exist.").printStackTrace()
            finish()
            return
        }
        try {
            pdfBitmapList = PDFUtil.pdfToBitmap(pdfFile)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val viewPager = findViewById<ViewPagerForPhotoView>(R.id.viewPagerPdfViewer)
        viewPager.adapter = PDFViewerPagerAdapter(
            supportFragmentManager,
            FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        )
        viewPager.setPageTransformer(true, ZoomOutPageTransformer())
        composeView = findViewById(R.id.compose_view)
    }

    fun setBottomUIComposeContent(composable: @Composable () -> Unit) {
        composeView.apply {
            ViewCompositionStrategy.DisposeOnLifecycleDestroyed(lifecycle = lifecycle)
            setContent {
                composable.invoke()
            }
        }
    }

    private class PDFViewerPagerAdapter(fm: FragmentManager, behavior: Int) :
        FragmentStatePagerAdapter(fm, behavior) {
        override fun getItem(position: Int): Fragment {
            val fragment: Fragment = PdfPageFragment()
            val args = Bundle()
            // Our object is just an integer :-P
            args.putInt(PdfPageFragment.ARG_POSITION, position)
            fragment.arguments = args
            return fragment
        }

        override fun getCount(): Int {
            return pdfBitmapList.size
        }
    }

    class PdfPageFragment : Fragment() {
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?, savedInstanceState: Bundle?
        ): View? {
            val rootView = inflater.inflate(R.layout.item_pdf_viewer, container, false)
            val args = arguments
            val position = args?.getInt(ARG_POSITION, 0) ?: 0
            val currentImage = pdfBitmapList[position]
            (rootView.findViewById<View>(R.id.imageViewItemPdfViewer) as TouchImageViewFling).setImageBitmap(
                currentImage
            )
            (rootView.findViewById<View>(R.id.textViewPdfViewerPageNumber) as AppCompatTextView).text =
                String.format(
                    Locale.getDefault(),
                    "%d OF %d",
                    position + 1,
                    pdfBitmapList.size
                )
            return rootView
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {}

        companion object {
            const val ARG_POSITION = "position"
        }
    }

    private class ZoomOutPageTransformer : ViewPager.PageTransformer {
        override fun transformPage(view: View, position: Float) {
            val viewToAnimateUTB = view.findViewById<View>(R.id.textViewPdfViewerPageNumber)
            val pageHeight = view.height
            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                viewToAnimateUTB.translationY = 0f
            } else if (position <= 1) { // [-1,1]
                viewToAnimateUTB.translationY = Math.abs(pageHeight * -position)
            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                viewToAnimateUTB.translationY = 0f
            }
        }
    }

    companion object {
        const val PDF_FILE_URI = "pdfFileUri"
        private const val TAG = "PDFViewerActivity"
        private var pdfBitmapList = LinkedList<Bitmap>()
    }
}