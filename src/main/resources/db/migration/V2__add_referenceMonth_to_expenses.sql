alter table expenses
add column reference_month INT;

update expenses set reference_month = EXTRACT(MONTH FROM due_date) - 1;

alter table expenses
alter column reference_month set not null;