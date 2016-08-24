// This file was generated by Mendix Modeler.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
// Special characters, e.g., é, ö, à, etc. are supported in comments.

package mvvm.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.mendix.core.Core;
import com.mendix.core.objectmanagement.member.MendixObjectReference;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.IMendixIdentifier;
import com.mendix.webui.CustomJavaAction;
import com.mendix.systemwideinterfaces.core.IMendixObject;
import mvvm.proxies.View;

public class UpdateModelFromView extends CustomJavaAction<java.util.List<IMendixObject>>
{
	private IMendixObject __SourceModel;
	private mvvm.proxies.Model SourceModel;
	private IMendixObject __ViewParameter1;
	private mvvm.proxies.View ViewParameter1;

	public UpdateModelFromView(IContext context, IMendixObject SourceModel, IMendixObject ViewParameter1)
	{
		super(context);
		this.__SourceModel = SourceModel;
		this.__ViewParameter1 = ViewParameter1;
	}

	@Override
	public java.util.List<IMendixObject> executeAction() throws Exception
	{
		this.SourceModel = __SourceModel == null ? null : mvvm.proxies.Model.initialize(getContext(), __SourceModel);

		this.ViewParameter1 = __ViewParameter1 == null ? null : mvvm.proxies.View.initialize(getContext(), __ViewParameter1);

		// BEGIN USER CODE
		HashMap<String, String> views = InitializeViews.getViewsForModel(SourceModel);
		ArrayList<IMendixObject> updateViews = new ArrayList<IMendixObject>();

		List<? extends MendixObjectReference> refs = SourceModel.getMendixObject().getReferences(getContext());
		
		CloneSource.clone(getContext(), __ViewParameter1, __SourceModel);
		
		for (String associationName : views.keySet()) {
			String objectName = views.get(associationName);
			//IMendixIdentifier source = (IMendixIdentifier) SourceModel.getMendixObject().getMember(getContext(), associationName).getValue(getContext());
			
			for (MendixObjectReference ref : refs) {
				if (ref.getName().equals(associationName) && !objectName.equals(__ViewParameter1.getType())) {
					IMendixIdentifier value = ref.getValue(getContext());
					IMendixObject obj = Core.retrieveId(getContext(), value);
					CloneSource.clone(getContext(), __SourceModel, obj);
					updateViews.add(obj);
				}
			}
			//IMendixObject view = InitializeViews.getView(getContext(), SourceModel, objectName, associationName).getObject();
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
		return "UpdateModelFromView";
	}

	// BEGIN EXTRA CODE
	// END EXTRA CODE
}
