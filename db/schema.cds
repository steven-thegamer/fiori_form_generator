using {cuid, managed} from '@sap/cds/common';

namespace db.schema;

aspect parentForm {
    form_parent : Association to one Form;
}

entity Form : cuid, managed {
    amount_of_columns : Integer @mandatory;
    file_name : String @mandatory;
    form_title : String @mandatory;
    form_alignment : Association to one FormTitleAlignment @mandatory;
    entry_children : Composition of many Entry on entry_children.form_parent = $self;
    form_buttons : Composition of many FormButtons on form_buttons.form_parent = $self;
}

entity FormTitleAlignment {
    key id : String(2);
    alignment : String;
}

entity Entry : cuid, managed, parentForm {
    entry_name : String @mandatory;
    entry_type : Association to one EntryTypes @mandatory;
    entry_column : Integer @mandatory;
    entry_row : Integer @mandatory;
    entry_editable : Boolean default true;

}

entity EntryTypes {
    key id : String(2);
    name : String;
}

entity FormButtons : cuid, managed, parentForm {
    button_name : String @mandatory;
    button_icon : Association to one ButtonIcons;
    order : Integer @mandatory;

}

entity ButtonIcons {
    key icon : String;
}