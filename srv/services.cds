using db.schema from '../db/schema';

service MainService {
    @odata.draft.enabled
    entity Forms as projection on schema.Form actions {
        action CreateNewForm() returns String;
    };
    entity Entries as projection on schema.Entry;
    entity FormTitleAlignments as projection on schema.FormTitleAlignment;
    entity EntryTypes as projection on schema.EntryTypes;
    entity FormButtons as projection on schema.FormButtons;
    entity ButtonIcons as projection on schema.ButtonIcons;
}