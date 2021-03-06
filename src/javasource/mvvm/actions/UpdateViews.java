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
import com.mendix.core.objectmanagement.member.MendixObjectReference;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.IMendixIdentifier;
import com.mendix.webui.CustomJavaAction;
import com.mendix.systemwideinterfaces.core.IMendixObject;
import communitycommons.XPath;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import mvvm.proxies.View;

public class UpdateViews extends CustomJavaAction<java.util.List<IMendixObject>>
{
	private IMendixObject __SourceModel;
	private mvvm.proxies.Model SourceModel;

	public UpdateViews(IContext context, IMendixObject SourceModel)
	{
		super(context);
		this.__SourceModel = SourceModel;
	}

	@Override
	public java.util.List<IMendixObject> executeAction() throws Exception
	{
		this.SourceModel = __SourceModel == null ? null : mvvm.proxies.Model.initialize(getContext(), __SourceModel);

		// BEGIN USER CODE
		//HashMap<String, String> views = InitializeViews.getViewsForModel(SourceModel);
		//ArrayList<IMendixObject> updateViews = new ArrayList<IMendixObject>();
		
		XPath<IMendixObject> xpath = XPath.create(getContext(), View.entityName)
				.eq(View.MemberNames.Views_Model, __SourceModel);
		List<IMendixObject> updateViews = xpath.all();
		
		for (IMendixObject view : updateViews) {
			CloneSource.clone(getContext(), __SourceModel, view);
		}

//		List<? extends MendixObjectReference> refs = SourceModel.getMendixObject().getReferences(getContext());
//		
//		for (String associationName : views.keySet()) {
//			String objectName = views.get(associationName);
//			//IMendixIdentifier source = (IMendixIdentifier) SourceModel.getMendixObject().getMember(getContext(), associationName).getValue(getContext());
//			
//			for (MendixObjectReference ref : refs) {
//				if (ref.getName().equals(associationName)) {
//					IMendixIdentifier value = ref.getValue(getContext());
//					if (value == null)
//						continue;
//					IMendixObject obj = Core.retrieveId(getContext(), value);
//					if (obj == null)
//						continue;
//					CloneSource.clone(getContext(), __SourceModel, obj);
//					updateViews.add(obj);
//				}
//			}
//			//IMendixObject view = InitializeViews.getView(getContext(), SourceModel, objectName, associationName).getObject();
//			
//		}

		return updateViews;
		// END USER CODE
	}

	/**
	 * Returns a string representation of this action
	 */
	@Override
	public String toString()
	{
		return "UpdateViews";
	}

	// BEGIN EXTRA CODE
	// END EXTRA CODE
}
