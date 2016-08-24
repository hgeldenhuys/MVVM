// This file was generated by Mendix Modeler.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
// Special characters, e.g., é, ö, à, etc. are supported in comments.

package mvvm.actions;

import com.mendix.core.Core;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.IMendixIdentifier;
import com.mendix.systemwideinterfaces.core.meta.IMetaAssociation;
import com.mendix.systemwideinterfaces.core.meta.IMetaObject;
import com.mendix.webui.CustomJavaAction;
import com.mendix.systemwideinterfaces.core.IMendixObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import mvvm.proxies.View;

public class InitializeViews extends CustomJavaAction<java.util.List<IMendixObject>>
{
	private IMendixObject __SourceModel;
	private mvvm.proxies.Model SourceModel;

	public InitializeViews(IContext context, IMendixObject SourceModel)
	{
		super(context);
		this.__SourceModel = SourceModel;
	}

	@Override
	public java.util.List<IMendixObject> executeAction() throws Exception
	{
		this.SourceModel = __SourceModel == null ? null : mvvm.proxies.Model.initialize(getContext(), __SourceModel);

		// BEGIN USER CODE

		HashMap<String, String> views = getViewsForModel(SourceModel);

//		if ((Path == null) || (Path.length()==0)){
//			Path = SourceModel.getMendixObject().getType().split("\\.")[0] + "View." + SourceModel.getMendixObject().getType().split("\\.")[1] + "View";
//		}
		
		ArrayList<IMendixObject> updateViews = new ArrayList<IMendixObject>();
		for (String associationName : views.keySet()) {
			String objectName = views.get(associationName);
			updateViews.add(getView(getContext(), SourceModel, objectName, associationName).getObject());
		}

		return updateViews;
		// END USER CODE
	}

	/**
	 * Returns a string representation of this action
	 */
	@Override
	public String toString()
	{
		return "InitializeViews";
	}

	// BEGIN EXTRA CODE
	public static IMendixIdentifier getView(IContext context, mvvm.proxies.Model SourceModel, String entityName, String associationName) throws Exception {
		IMendixIdentifier iView = (IMendixIdentifier) SourceModel.getMendixObject().getMember(context, associationName).getValue(context);

		if ((iView == null) || (iView.getObject() == null)) {

			IMendixObject targetView = Core.instantiate(context, entityName);
			CloneSource.clone(context, SourceModel.getMendixObject(), targetView);

			SourceModel.getMendixObject().setValue(context, associationName, targetView.getId());
			View baseview = View.initialize(context, targetView);
			
			baseview.setViews_Model(SourceModel);
			
			
			
			//ErrorSolutionsView.initialize(context, targetView).setViewSolutions(SourceModel);
			
			//targetView.getMember(context, associationName)..setValue(SourceModel.getMendixObject());
			
			return targetView.getId();
		} else {
			return iView;
		}
	}

	public static HashMap<String, String> getViewsForModel(mvvm.proxies.Model SourceModel) {
		HashMap<String, String> views = new HashMap<String, String>();
		Collection<? extends IMetaAssociation> values = SourceModel.getMendixObject().getMetaObject().getMetaAssociationsParent();

		if (!viewsCache.containsKey(SourceModel.getMendixObject().getType())) {
			for (IMetaAssociation association : values) {
				if (association.getParent().getName().equals("Collection.Item"))
					continue;
				IMetaObject superObject = association.getParent().getSuperObject();
				while ((superObject != null) && !superObject.getName().equals("MVVM.View"))
					superObject = superObject.getSuperObject();
				if (superObject != null) {
					String name = association.getParent().getName();
					if (superObject.getName().equals(View.getType())) {
						views.put(association.getName(), name);
					}
				}
			}
			viewsCache.put(SourceModel.getMendixObject().getType(), views);
			return views;
		}

		return viewsCache.get(SourceModel.getMendixObject().getType());
	}

	private static HashMap<String, HashMap<String, String>> viewsCache = new HashMap<String, HashMap<String, String>>();
	// END EXTRA CODE
}