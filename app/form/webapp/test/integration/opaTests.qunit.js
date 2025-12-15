sap.ui.require(
    [
        'sap/fe/test/JourneyRunner',
        'form/test/integration/FirstJourney',
		'form/test/integration/pages/FormsList',
		'form/test/integration/pages/FormsObjectPage'
    ],
    function(JourneyRunner, opaJourney, FormsList, FormsObjectPage) {
        'use strict';
        var JourneyRunner = new JourneyRunner({
            // start index.html in web folder
            launchUrl: sap.ui.require.toUrl('form') + '/index.html'
        });

       
        JourneyRunner.run(
            {
                pages: { 
					onTheFormsList: FormsList,
					onTheFormsObjectPage: FormsObjectPage
                }
            },
            opaJourney.run
        );
    }
);