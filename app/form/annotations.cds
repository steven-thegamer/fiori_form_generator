using MainService as service from '../../srv/services';
using from '../../db/schema';

annotate service.Forms with @(
    UI.LineItem : [
        {
            $Type : 'UI.DataField',
            Value : file_name,
            Label : '{i18n>FileName}',
        },
        {
            $Type : 'UI.DataField',
            Value : form_title,
            Label : '{i18n>Title}',
        },
        {
            $Type : 'UI.DataField',
            Value : createdAt,
        },
        {
            $Type : 'UI.DataFieldForAction',
            Action : 'MainService.CreateNewForm',
            Label : '{i18n>CreateNewForm}',
            Inline : true,
            @UI.Importance : #High,
        },
    ],
    UI.Facets : [
        {
            $Type : 'UI.ReferenceFacet',
            Label : '{i18n>General}',
            ID : 'i18nGeneral',
            Target : '@UI.FieldGroup#i18nGeneral',
        },
        {
            $Type : 'UI.ReferenceFacet',
            Label : '{i18n>Administration}',
            ID : 'i18nAdministration',
            Target : '@UI.FieldGroup#i18nAdministration',
        },
        {
            $Type : 'UI.ReferenceFacet',
            Label : '{i18n>Entries}',
            ID : 'i18nEntries',
            Target : 'entry_children/@UI.LineItem#i18nEntries',
        },
        {
            $Type : 'UI.ReferenceFacet',
            Label : '{i18n>Buttons}',
            ID : 'i18nButtons',
            Target : 'form_buttons/@UI.LineItem#i18nButtons',
        },
    ],
    UI.FieldGroup #i18nGeneral : {
        $Type : 'UI.FieldGroupType',
        Data : [
            {
                $Type : 'UI.DataField',
                Value : amount_of_columns,
                Label : '{i18n>ColumnsAmount}',
            },
            {
                $Type : 'UI.DataField',
                Value : file_name,
                Label : '{i18n>FileName}',
            },
            {
                $Type : 'UI.DataField',
                Value : form_title,
                Label : '{i18n>FormTitle}',
            },
            {
                $Type : 'UI.DataField',
                Value : form_alignment_id,
                Label : '{i18n>FormTitleAlignment}',
            },
        ],
    },
    UI.FieldGroup #i18nAdministration : {
        $Type : 'UI.FieldGroupType',
        Data : [
            {
                $Type : 'UI.DataField',
                Value : createdAt,
            },
            {
                $Type : 'UI.DataField',
                Value : createdBy,
            },
            {
                $Type : 'UI.DataField',
                Value : modifiedAt,
            },
            {
                $Type : 'UI.DataField',
                Value : modifiedBy,
            },
        ],
    },
);

annotate service.Entries with @(
    UI.LineItem #i18nEntries : [
        {
            $Type : 'UI.DataField',
            Value : entry_name,
            Label : 'Name',
        },
        {
            $Type : 'UI.DataField',
            Value : entry_column,
            Label : '{i18n>Column}',
        },
        {
            $Type : 'UI.DataField',
            Value : entry_row,
            Label : '{i18n>Row}',
        },
        {
            $Type : 'UI.DataField',
            Value : entry_type_id,
            Label : '{i18n>EntryType}',
        },
        {
            $Type : 'UI.DataField',
            Value : entry_editable,
            Label : '{i18n>Editable}',
        },
    ]
);

annotate service.EntryTypes with {
    id @(
        Common.Text : name,
        Common.Text.@UI.TextArrangement : #TextOnly,
        Common.ValueList : {
            $Type : 'Common.ValueListType',
            CollectionPath : 'EntryTypes',
            Parameters : [
                {
                    $Type : 'Common.ValueListParameterInOut',
                    LocalDataProperty : id,
                    ValueListProperty : 'id',
                },
            ],
            Label : '{i18n>EntryTypes}',
        },
        Common.ValueListWithFixedValues : false,
    )
};

annotate service.Forms with {
    form_alignment @(
        Common.Text : form_alignment.alignment,
        Common.Text.@UI.TextArrangement : #TextOnly,
        Common.ValueList : {
            $Type : 'Common.ValueListType',
            CollectionPath : 'FormTitleAlignments',
            Parameters : [
                {
                    $Type : 'Common.ValueListParameterInOut',
                    LocalDataProperty : form_alignment_id,
                    ValueListProperty : 'id',
                },
            ],
            Label : '{i18n>Formtitlealignments}',
        },
        Common.ValueListWithFixedValues : true,
    )
};

annotate service.FormTitleAlignments with {
    id @(
        Common.Text : alignment,
        Common.Text.@UI.TextArrangement : #TextOnly,
)};

annotate service.Entries with {
    entry_name @Common.FieldControl : #Mandatory
};

annotate service.Entries with {
    entry_type @(
        Common.Text : entry_type.name,
        Common.Text.@UI.TextArrangement : #TextOnly,
        Common.ValueList : {
            $Type : 'Common.ValueListType',
            CollectionPath : 'EntryTypes',
            Parameters : [
                {
                    $Type : 'Common.ValueListParameterInOut',
                    LocalDataProperty : entry_type_id,
                    ValueListProperty : 'id',
                },
            ],
            Label : '{i18n>EntryTypes}',
        },
        Common.ValueListWithFixedValues : true,
        )
};

annotate service.FormButtons with @(
    UI.LineItem #i18nButtons : [
        {
            $Type : 'UI.DataField',
            Value : button_name,
            Label : '{i18n>ButtonLabel}',
        },
        {
            $Type : 'UI.DataField',
            Value : button_icon_icon,
            Label : '{i18n>Buttonicon}',
        },
        {
            $Type : 'UI.DataField',
            Value : order,
            Label : '{i18n>Order}',
        },
    ]
);

annotate service.FormButtons with {
    button_icon @(
        Common.ValueList : {
            $Type : 'Common.ValueListType',
            CollectionPath : 'ButtonIcons',
            Parameters : [
                {
                    $Type : 'Common.ValueListParameterInOut',
                    LocalDataProperty : button_icon_icon,
                    ValueListProperty : 'icon',
                },
            ],
            Label : '{i18n>Icons}',
        },
        Common.ValueListWithFixedValues : false,
)};

