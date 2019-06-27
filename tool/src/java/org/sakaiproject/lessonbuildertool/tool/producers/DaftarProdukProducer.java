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
import uk.org.ponder.rsf.components.UIInternalLink;
import uk.org.ponder.rsf.components.UILink;
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
import org.sakaiproject.lessonbuildertool.SimplePageProduk;
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
public class DaftarProdukProducer implements ViewComponentProducer, NavigationCaseReporter, ViewParamsReporter {
	private SimplePageBean simplePageBean;
	private ShowPageProducer showPageProducer;
	private SimplePageToolDao simplePageToolDao;

	public MessageLocator messageLocator;
        public LocaleGetter localeGetter;                                                                                             

	public static final String VIEW_ID = "DaftarProduk";

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

        	UIOutput.make(tofill, "daftar-produk", "Daftar Produk");
        	UIBranchContainer row_label = UIBranchContainer.make(tofill, "produk-label:");
	            UIOutput.make(row_label, "kode_produk_label", "Kode Produk");
	            UIOutput.make(row_label, "nama_produk_label", "Nama Produk");
	            UIOutput.make(row_label, "deskripsi_label", "Deskripsi");
	            UIOutput.make(row_label, "harga_label", "Harga");
				UIOutput.make(row_label, "stok_label", "Stok");
				UIOutput.make(row_label, "kode_jenis_produk_label", "Jenis Produk");
				UIOutput.make(row_label, "label_action", "Action");
        	List<SimplePageProduk> listProduk;
        	listProduk = simplePageToolDao.getAllProduk();

        	for (SimplePageProduk item : listProduk){
            //Create a new <li> element
	            UIBranchContainer row = UIBranchContainer.make(tofill, "produk-row:");
	            UIOutput.make(row, "kode_produk", Integer.toString(item.getKodeProduk()));
	            UIOutput.make(row, "nama_produk", item.getNamaProduk());
	            UIOutput.make(row, "harga", Integer.toString(item.getHarga()));
	            UIOutput.make(row, "deskripsi", item.getDeskripsi());
				UIOutput.make(row, "stok", Integer.toString(item.getStok()));
				UIOutput.make(row, "kode_jenis_produk", Integer.toString(item.getKodeJenisProduk()));
				
				GeneralViewParameters Historyparams = (GeneralViewParameters) viewparams;
	     		Historyparams.setProdukId(item.getKodeProduk());
	     		createStandardLink(DaftarProdukProducer.VIEW_ID,row,"delete_id","Delete",Historyparams);
        	}

        	UIForm form = UIForm.make(tofill, "tambah-produk");
        	UICommand.make(form, "submit", "Tambah Produk","#{simplePageBean.addProduk}");
	}
	
	private void createStandardLink(String viewID, UIContainer tofill, String ID, String message, SimpleViewParameters params) {
		params.viewID = viewID;
		UILink link = UIInternalLink.make(tofill, ID, message , params);
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
		//togo.add(new NavigationCase("tambah-produk", new SimpleViewParameters(ProdukProducer.VIEW_ID)));
		togo.add(new NavigationCase("failure", new SimpleViewParameters(ShowPageProducer.VIEW_ID)));

		return togo;
	}
}