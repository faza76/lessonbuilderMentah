package org.sakaiproject.lessonbuildertool.tool.producers;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import lombok.extern.slf4j.Slf4j;
import uk.org.ponder.messageutil.MessageLocator;
import uk.org.ponder.localeutil.LocaleGetter;                                                                                          
import uk.org.ponder.rsf.components.UIBranchContainer;
import uk.org.ponder.rsf.components.UIBoundBoolean;
import uk.org.ponder.rsf.components.UICommand;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIELBinding;
import uk.org.ponder.rsf.components.UIForm;
import uk.org.ponder.rsf.components.UIInput;
import uk.org.ponder.rsf.components.UIOutput;
import uk.org.ponder.rsf.components.decorators.UIFreeAttributeDecorator;
import uk.org.ponder.rsf.components.decorators.UITooltipDecorator;
import uk.org.ponder.rsf.evolvers.TextInputEvolver;
import uk.org.ponder.rsf.flow.jsfnav.NavigationCase;
import uk.org.ponder.rsf.flow.jsfnav.NavigationCaseReporter;
import uk.org.ponder.rsf.view.ComponentChecker;
import uk.org.ponder.rsf.view.ViewComponentProducer;
import uk.org.ponder.rsf.viewstate.SimpleViewParameters;
import uk.org.ponder.rsf.viewstate.ViewParameters;
import uk.org.ponder.rsf.viewstate.ViewParamsReporter;
import org.sakaiproject.exception.IdUnusedException;
import org.sakaiproject.lessonbuildertool.SimplePage;
import org.sakaiproject.lessonbuildertool.SimplePageExample;

//glossary
import org.sakaiproject.lessonbuildertool.SimplePageGlossary;

// modifikasi penjualan
import org.sakaiproject.lessonbuildertool.SimplePageProduk;
// modifikasi penjualan

import org.sakaiproject.lessonbuildertool.SimplePageImpl;
import org.sakaiproject.lessonbuildertool.SimplePageItem;
import org.sakaiproject.lessonbuildertool.tool.beans.SimplePageBean;
import org.sakaiproject.lessonbuildertool.model.SimplePageToolDao;
import org.sakaiproject.lessonbuildertool.tool.view.GeneralViewParameters;
import org.sakaiproject.tool.cover.SessionManager;

/**
 * Uses an FCK editor to edit blocks of text.
 * 
 * @author Joshua Ryan josh@asu.edu alt^I
 * @author Eric Jeney <jeney@rutgers.edu>
 */
@Slf4j
public class ShowProdukProducer implements ViewComponentProducer, NavigationCaseReporter, ViewParamsReporter {
	private SimplePageBean simplePageBean;
	private ShowPageProducer showPageProducer;
	private SimplePageToolDao simplePageToolDao;

	public MessageLocator messageLocator;
        public LocaleGetter localeGetter;                                                                                             

	public static final String VIEW_ID = "ShowProduk";

	public String getViewID() {
		return VIEW_ID;
	}

	public void fillComponents(UIContainer tofill, ViewParameters viewparams, ComponentChecker checker) {

		GeneralViewParameters gparams = (GeneralViewParameters) viewparams;

		if (gparams.getSendingPage() != -1) {
			// will fail if page not in this site
			// security then depends upon making sure that we only deal with this page
			try {
				simplePageBean.updatePageObject(gparams.getSendingPage());
			} catch (Exception e) {
				log.info("EditPage permission exception " + e);
				return;
			}
		}

        UIOutput.make(tofill, "html").decorate(new UIFreeAttributeDecorator("lang", localeGetter.get().
        	getLanguage())).decorate(new UIFreeAttributeDecorator("xml:lang", localeGetter.get().getLanguage()));  

        	// UIOutput.make(tofill, "produk", "Daftar Produk");
        	// UIOutput.make(tofill, "label_kodeproduk", "Kode Produk");
        	// UIOutput.make(tofill, "label_namaproduk", "Nama Produk");
        	// UIOutput.make(tofill, "label_deskripsi", "Deskripsi");
        	// UIOutput.make(tofill, "label_stok", "Stok");

        	// UIForm form = UIForm.make(tofill, "add-example");
        	// UIInput.make(form, "glossary_term", "#{simplePageBean.termG}");
        	// UIInput.make(form, "glossary_description", "#{simplePageBean.descriptionG}");
        	// UIInput.make(form, "glossary_category", "#{simplePageBean.categoryG}");
        	// UICommand.make(form, "submit","Add Glossary", "#{simplePageBean.processActionSubmit}");

        	List<SimplePageProduk> listProduk;
        	listProduk = simplePageToolDao.getAllProduk();

			UIBranchContainer row_label = UIBranchContainer.make(tofill,"label-produk:");
            UIOutput.make(row_label,"label_kodeproduk","Kode Produk");
            UIOutput.make(row_label,"label_namaproduk", "Nama Produk");
            UIOutput.make(row_label,"label_deskripsi", "Deskripsi");
            UIOutput.make(row_label,"label_stok","Stok");
            UIOutput.make(row_label,"label_harga","Harga");


         for (SimplePageProduk item : listProduk){
            //Create a new <li> element
             UIBranchContainer row_isi = UIBranchContainer.make(tofill, "list-produk:");
             UIOutput.make(row_isi, "list_kodeproduk", Integer.toString(item.getKodeProduk()));
             UIOutput.make(row_isi, "list_namaproduk", item.getNamaProduk());
             UIOutput.make(row_isi, "list_deskripsi", item.getDeskripsi());
             UIOutput.make(row_isi, "list_stok", Integer.toString(item.getStok()));
             UIOutput.make(row_isi, "list_harga", Integer.toString(item.getHarga()));

         }
	}

	public void setShowPageProducer(ShowPageProducer showPageProducer) {
		this.showPageProducer = showPageProducer;
	}

	public void setSimplePageBean(SimplePageBean simplePageBean) {
		this.simplePageBean = simplePageBean;
	}

	public void setSimplePageToolDao(SimplePageToolDao s) {
		simplePageToolDao = s;
	}

	public ViewParameters getViewParameters() {
		return new GeneralViewParameters();
	}

	public List reportNavigationCases() {
		List<NavigationCase> togo = new ArrayList<NavigationCase>();
		togo.add(new NavigationCase(null, new SimpleViewParameters(ShowPageProducer.VIEW_ID)));
		togo.add(new NavigationCase("success", new SimpleViewParameters(ShowPageProducer.VIEW_ID)));
		togo.add(new NavigationCase("cancel", new SimpleViewParameters(ShowPageProducer.VIEW_ID)));
		togo.add(new NavigationCase("failure", new SimpleViewParameters(ShowPageProducer.VIEW_ID)));

		return togo;
	}
}