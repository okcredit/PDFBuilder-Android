package tech.okcredit.create_pdf.activity

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.content.ContextCompat
import tech.okcredit.create_pdf.R
import tech.okcredit.create_pdf.utils.FileManager
import tech.okcredit.create_pdf.utils.PDFUtil
import tech.okcredit.create_pdf.utils.PDFUtil.PDFUtilListener
import tech.okcredit.create_pdf.views.PDFBody
import tech.okcredit.create_pdf.views.PDFFooterView
import tech.okcredit.create_pdf.views.PDFHeaderView
import tech.okcredit.create_pdf.views.basic.PDFPageBreakView
import tech.okcredit.create_pdf.views.basic.PDFVerticalView
import java.io.File
import java.util.Locale

abstract class PDFCreatorActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var layoutPageParent: LinearLayout
    lateinit var layoutPrintPreview: LinearLayout
    var textViewPdfGeneratingLoader: LinearLayout? = null
    var textViewPageNumber: TextView? = null
    var textViewPreviewNotAvailable: TextView? = null
    var imageViewPDFPreview: AppCompatImageView? = null
    lateinit var buttonNextPage: ImageButton
    lateinit var buttonPreviousPage: ImageButton
    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    lateinit var toolbarTailIcon: ImageButton
    lateinit var buttonContainer: LinearLayout
    lateinit var primaryButton: Button
    lateinit var pageNumberContainer: LinearLayout
    lateinit var secondaryButton: Button
    lateinit var primaryCtaButton: Button
    lateinit var composeView: ComposeView
    var pagePreviewBitmapList = ArrayList<Bitmap>()
    var savedPDFFile: File? = null
    private var heightRequiredByHeader = 0
    private var heightRequiredByFooter = 0
    private var selectedPreviewPage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdfcreator)
        layoutPageParent = findViewById(R.id.layoutPdfPreview)
        textViewPdfGeneratingLoader = findViewById(R.id.textViewPdfGeneratingLoader)
        layoutPrintPreview = findViewById(R.id.layoutPrintPreview)
        imageViewPDFPreview = layoutPrintPreview.findViewById(R.id.imagePreviewPdf)
        textViewPageNumber = layoutPrintPreview.findViewById(R.id.textViewPreviewPageNumber)
        pageNumberContainer = layoutPrintPreview.findViewById(R.id.pageNumberContainer)
        textViewPreviewNotAvailable =
            layoutPrintPreview.findViewById(R.id.textViewPreviewPDFNotSupported)
        layoutPageParent.removeAllViews()
        buttonNextPage = layoutPrintPreview.findViewById(R.id.buttonNextPage)
        buttonNextPage.setOnClickListener(this)
        buttonPreviousPage = layoutPrintPreview.findViewById(R.id.buttonPreviousPage)
        buttonPreviousPage.setOnClickListener(this)
        toolbar = layoutPrintPreview.findViewById(R.id.toolbar)
        toolbarTailIcon = layoutPrintPreview.findViewById(R.id.toolbar_tail_icon)
        buttonContainer = layoutPrintPreview.findViewById(R.id.button_container)
        primaryButton = layoutPrintPreview.findViewById(R.id.primary_button)
        secondaryButton = layoutPrintPreview.findViewById(R.id.secondary_button)
        primaryCtaButton = layoutPrintPreview.findViewById(R.id.primary_cta_button)
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

    fun enableCtaButtons(
        primaryButtonTitle: String, primaryButtonAction: (savedPDFFile: File?) -> Unit,
        secondaryButtonTitle: String? = null, secondaryButtonAction: (savedPDFFile: File?) -> Unit = {}
    ) {
        if (secondaryButtonTitle != null) {
            primaryCtaButton.visibility = View.GONE
            buttonContainer.visibility = View.VISIBLE

            primaryButton.text = primaryButtonTitle
            primaryButton.setOnClickListener {
                primaryButtonAction.invoke(savedPDFFile)
            }

            secondaryButton.text = secondaryButtonTitle
            secondaryButton.setOnClickListener {
                secondaryButtonAction.invoke(savedPDFFile)
            }
        } else {
            primaryCtaButton.visibility = View.VISIBLE
            buttonContainer.visibility = View.GONE

            primaryCtaButton.text = primaryButtonTitle
            primaryCtaButton.setOnClickListener {
                primaryButtonAction.invoke(savedPDFFile)
            }
        }
    }

    fun setToolbarTitle(
        title: String, @DrawableRes icon: Int? = null,
        onIconClicked: () -> Unit = {}, onBackClicked: (() -> Unit)? = null
    ) {
        toolbar.visibility = View.VISIBLE
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            if (onBackClicked != null) {
                onBackClicked.invoke()
            } else {
                onBackPressed()
            }
        }
        supportActionBar?.title = title

        if (icon != null) {
            toolbarTailIcon.visibility = View.VISIBLE
            toolbarTailIcon.setImageResource(icon)
            toolbarTailIcon.setOnClickListener {
                onIconClicked.invoke()
            }
        } else {
            toolbarTailIcon.visibility = View.GONE
        }
    }

    fun setBackgroundColor(@ColorInt color: Int) {
        imageViewPDFPreview?.setBackgroundColor(color)
    }

    fun createPDF(fileName: String, pdfUtilListener: PDFUtilListener, @DrawableRes watermarkDrawable: Int? = null) {
        val bodyViewList = ArrayList<View?>()
        var header: View? = null
        if (getHeaderView(0) != null) {
            header = getHeaderView(0)!!.view
            header.setTag(PDFHeaderView::class.java.simpleName)
            bodyViewList.add(header)
            addViewToTempLayout(layoutPageParent, header)
        }
        if (getBodyViews() != null) {
            for (pdfView in getBodyViews()!!.childViewList) {
                val bodyView = pdfView.view
                if (pdfView is PDFPageBreakView) {
                    bodyView.tag = PDFPageBreakView::class.java.simpleName
                } else {
                    bodyView.tag = PDFBody::class.java.simpleName
                }
                bodyViewList.add(bodyView)
                addViewToTempLayout(layoutPageParent, bodyView)
            }
        }
        var footer: View? = null
        val pdfFooterView = getFooterView(0)
        if (pdfFooterView != null && pdfFooterView.view.childCount > 1) {
            // pdfFooterView.getView().getChildCount() > 1, because first view is ALWAYS empty space filler.
            footer = pdfFooterView.view
            footer.setTag(PDFFooterView::class.java.simpleName)
            addViewToTempLayout(layoutPageParent, footer)
        }
        createPDFFromViewList(header, footer, watermarkDrawable, bodyViewList, fileName, object : PDFUtilListener {
            override fun pdfGenerationSuccess(savedPDFFile: File) {
                try {
                    pagePreviewBitmapList.clear()
                    pagePreviewBitmapList.addAll(PDFUtil.pdfToBitmap(savedPDFFile))
                    textViewPdfGeneratingLoader!!.visibility = View.GONE
                    layoutPrintPreview!!.visibility = View.VISIBLE
                    selectedPreviewPage = 0
                    imageViewPDFPreview!!.setImageBitmap(pagePreviewBitmapList[selectedPreviewPage])
                    if (pagePreviewBitmapList.size > 1) {
                        textViewPageNumber!!.text = String.format(
                            Locale.getDefault(),
                            "%d of %d",
                            selectedPreviewPage + 1,
                            pagePreviewBitmapList.size
                        )
                        pageNumberContainer.visibility = View.VISIBLE
                    } else {
                        pageNumberContainer.visibility = View.GONE
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    imageViewPDFPreview!!.visibility = View.GONE
                    textViewPageNumber!!.visibility = View.GONE
                    buttonNextPage!!.visibility = View.GONE
                    buttonPreviousPage!!.visibility = View.GONE
                    textViewPreviewNotAvailable!!.visibility = View.VISIBLE
                }
                this@PDFCreatorActivity.savedPDFFile = savedPDFFile
                pdfUtilListener.pdfGenerationSuccess(savedPDFFile)
            }

            override fun pdfGenerationFailure(exception: Exception) {
                pdfUtilListener.pdfGenerationFailure(exception)
            }
        })
    }

    /**
     * Creates a paginated PDF page views from list of views those are already rendered on screen
     * (Only rendered views can give height)
     *
     * @param tempViewList list of views to create pdf views from, view should be already rendered to screen
     */
    private fun createPDFFromViewList(
        headerView: View?,
        footerView: View?,
        @DrawableRes watermarkDrawable: Int?,
        tempViewList: ArrayList<View?>,
        filename: String,
        pdfUtilListener: PDFUtilListener
    ) {
        tempViewList[tempViewList.size - 1]!!.post {
            // Clean temp folder
            val fileManager = FileManager.instance
            fileManager?.cleanTempFolder(applicationContext)

            // get height per page
            val HEIGHT_ALLOTTED_PER_PAGE =
                resources.getDimensionPixelSize(R.dimen.pdf_height) - resources.getDimensionPixelSize(
                    R.dimen.pdf_margin_vertical
                ) * 2
            runOnUiThread {
                val pdfPageViewList: MutableList<View> = ArrayList()
                var currentPDFLayout = layoutInflater.inflate(
                    R.layout.item_pdf_page,
                    layoutPageParent,
                    false
                ) as FrameLayout
                currentPDFLayout.setBackgroundColor(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.colorWhite
                    )
                )
                pdfPageViewList.add(currentPDFLayout)

                var currentPDFView = PDFVerticalView(applicationContext).view
                val verticalPageLayoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT, 0f
                )
                currentPDFView.layoutParams = verticalPageLayoutParams
                currentPDFLayout.addView(currentPDFView)

                val watermarkViewForFirstPage = getWatermarkViewFromDrawable(watermarkDrawable)
                if (watermarkViewForFirstPage != null) {
                    currentPDFLayout.addView(watermarkViewForFirstPage)
                }

                var currentPageHeight = 0
                if (headerView != null) {
                    // If item is a page header, store its height so we can add it to all pages without waiting to render it every time
                    heightRequiredByHeader = headerView.height
                }
                if (footerView != null) {
                    // If item is a page header, store its height so we can add it to all pages without waiting to render it every time
                    heightRequiredByFooter = footerView.height
                }
                var pageIndex = 1
                for (i in tempViewList.indices) {
                    val viewItem = tempViewList[i]
                    var isPageBreakView = false
                    if (viewItem!!.tag != null && viewItem.tag is String) {
                        isPageBreakView = PDFPageBreakView::class.java.simpleName.equals(
                            viewItem.tag as String, ignoreCase = true
                        )
                    }
                    if (currentPageHeight + viewItem.height > HEIGHT_ALLOTTED_PER_PAGE) {
                        // this will be exceed current page, create a new page and add this view to that page
                        currentPDFLayout = layoutInflater.inflate(
                            R.layout.item_pdf_page,
                            layoutPageParent,
                            false
                        ) as FrameLayout
                        currentPDFLayout.setBackgroundColor(
                            ContextCompat.getColor(
                                applicationContext,
                                R.color.colorWhite
                            )
                        )
                        pdfPageViewList.add(currentPDFLayout)
                        currentPageHeight = 0

                        currentPDFView = PDFVerticalView(applicationContext).view
                        currentPDFView.layoutParams = verticalPageLayoutParams
                        currentPDFLayout.addView(currentPDFView)

                        val watermarkViewForOtherPage = getWatermarkViewFromDrawable(watermarkDrawable)
                        if (watermarkViewForFirstPage != null) {
                            currentPDFLayout.addView(watermarkViewForOtherPage)
                        }

                        // Add page header again
                        if (heightRequiredByHeader > 0) {
                            // If height is available, only then add header
                            val layoutHeader = getHeaderView(pageIndex)!!.view
                            addViewToTempLayout(layoutPageParent, layoutHeader)
                            currentPageHeight += heightRequiredByHeader
                            layoutPageParent!!.removeView(layoutHeader)
                            currentPDFView.addView(layoutHeader)
                            pageIndex = pageIndex + 1
                        }
                    }
                    if (!isPageBreakView) {
                        // if not empty view, add
                        currentPageHeight += viewItem.height
                        layoutPageParent!!.removeView(viewItem)
                        currentPDFView.addView(viewItem)
                    } else {
                        Log.d(TAG, "run: This is PageBreakView")
                        currentPageHeight = HEIGHT_ALLOTTED_PER_PAGE
                    }

                    // See if we have enough space to add Next View with Footer
                    // We we don't, add Footer View to current page
                    // Height required to add this view in current page
                    var heightRequiredToAddNextView = 0
                    var shouldAddFooterNow = false
                    if (tempViewList.size > i + 1) {
                        // Check if we can add CURRENT_VIEW + NEXT_VIEW + FOOTER in current page
                        val nextViewItem = tempViewList[i + 1]
                        heightRequiredToAddNextView = nextViewItem!!.height
                        if (currentPageHeight + heightRequiredToAddNextView + heightRequiredByFooter > HEIGHT_ALLOTTED_PER_PAGE) {
                            shouldAddFooterNow = true
                        }
                    } else {
                        // Add Views are already added, we should add footer next
                        shouldAddFooterNow = true
                    }
                    if (isPageBreakView || shouldAddFooterNow) {
                        // Cannot Add Next View with Footer in current Page
                        // Add Footer View to Current Page
                        if (heightRequiredByFooter > 0) {
                            // Footer is NOT prematurely added like header, so we need to subtract 1 from pageIndex
                            val layoutFooter = getFooterView(pageIndex - 1).view
                            addViewToTempLayout(layoutPageParent, layoutFooter)
                            layoutPageParent!!.removeView(layoutFooter)
                            currentPDFView.addView(layoutFooter)
                            currentPageHeight = HEIGHT_ALLOTTED_PER_PAGE
                        }
                    }
                }
                PDFUtil.getInstance().generatePDF(
                    pdfPageViewList, fileManager?.createTempFileWithName(
                        applicationContext, "$filename.pdf", false
                    )?.absolutePath, pdfUtilListener
                )
            }
        }
    }

    private fun addViewToTempLayout(layoutPageParent: LinearLayout?, viewToAdd: View?) {
        layoutPageParent!!.addView(viewToAdd)
    }

    private fun getWatermarkViewFromDrawable(@DrawableRes watermarkDrawable: Int?): ImageView? {
        if (watermarkDrawable == null) {
            return null
        }
        val imageView = ImageView(applicationContext)
        imageView.setImageResource(watermarkDrawable)
        imageView.scaleX = 0.75f
        imageView.scaleY = 0.75f
        imageView.alpha = 0.1f
        return imageView
    }

    override fun onClick(v: View) {
        if (v === buttonNextPage) {
            if (selectedPreviewPage == pagePreviewBitmapList.size - 1) {
                return
            }
            selectedPreviewPage = selectedPreviewPage + 1
            imageViewPDFPreview!!.setImageBitmap(pagePreviewBitmapList[selectedPreviewPage])
            textViewPageNumber!!.text = String.format(
                Locale.getDefault(),
                "%d of %d",
                selectedPreviewPage + 1,
                pagePreviewBitmapList.size
            )
        } else if (v === buttonPreviousPage) {
            if (selectedPreviewPage == 0) {
                return
            }
            selectedPreviewPage = selectedPreviewPage - 1
            imageViewPDFPreview!!.setImageBitmap(pagePreviewBitmapList[selectedPreviewPage])
            textViewPageNumber!!.text = String.format(
                Locale.getDefault(),
                "%d of %d",
                selectedPreviewPage + 1,
                pagePreviewBitmapList.size
            )
        }
    }

    /**
     * Get header per page, starts with page: 0
     * MAKE SURE HEIGHT OF EVERY HEADER IS SAME FOR EVERY PAGE
     *
     * @param forPage page number
     * @return View for header
     */
    protected abstract fun getHeaderView(forPage: Int): PDFHeaderView?

    /**
     * Content that has to be paginated
     *
     * @return PDFBody, which is a List of Views
     */

    protected abstract fun getBodyViews(): PDFBody?

    /**
     * Get header per page, starts with page: 0
     * MAKE SURE HEIGHT OF EVERY FOOTER IS SAME FOR EVERY PAGE
     *
     * @param forPage page number
     * @return View for header
     */
    protected abstract fun getFooterView(forPage: Int): PDFFooterView

    protected abstract fun onNextClicked(savedPDFFile: File?)

    companion object {
        private const val TAG = "PDFCreatorActivity"
    }
}